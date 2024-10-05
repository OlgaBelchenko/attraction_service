package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.AttractionsRequest;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.NoSuchAttractionException;
import com.example.attraction_service.exception.NoSuchAttractionTypeException;
import com.example.attraction_service.exception.NoSuchLocalityException;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.AttractionService;
import com.example.attraction_service.utils.AttractionMapper;
import com.example.attraction_service.utils.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;
    private final LocalityRepository localityRepository;
    private final LocalityMapper localityMapper;


    @Override
    public void addAttraction(AttractionDto attractionDto) {
        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        if (attractionRepository.existsByName(attraction.getName())) return;
        attractionRepository.save(attraction);
    }

    @Override
    public List<AttractionDto> getAllAttractions(AttractionsRequest attractionsRequest) {
        List<Attraction> attractions = attractionRepository.findAll();

        if (!attractions.isEmpty()) {
            if (attractionsRequest.sortByName()) {
                attractions.sort(Comparator.comparing(Attraction::getName));
            }

            if (attractionsRequest.attractionType() != null) {
                try {
                    AttractionType attractionType = AttractionType.valueOf(attractionsRequest.attractionType());
                    attractions = attractions.stream().filter(attr -> attr.getAttractionType().equals(attractionType)).toList();
                } catch (IllegalArgumentException e) {
                    throw new NoSuchAttractionTypeException("Тип достопримечательности " + attractionsRequest.attractionType() + " не существует!");
                }
            }
        }

        return attractions.stream().map(attractionMapper::mapToAttractionDto).toList();
    }

    @Override
    public List<AttractionDto> getAllLocalityAttractions(LocalityDto localityDto) {
        Locality locality = localityMapper.mapToLocality(localityDto);
        List<AttractionDto> attractionDtos;
        if (localityRepository.existsByName(locality.getName())) {
            attractionDtos = attractionRepository.findByLocality(locality).stream().map(attractionMapper::mapToAttractionDto).toList();
        } else {
            throw new NoSuchLocalityException("Достопримечательности с названием " + localityDto.name() + " не существует!");
        }
        return attractionDtos;
    }

    @Override
    public void updateAttraction(AttractionDto attractionDto) {
        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        if (attractionRepository.existsByName(attraction.getName())) {
            attractionRepository.save(attraction);
        } else {
            throw new NoSuchAttractionException("Достопримечательности с названием " + attractionDto.name() + " не существует!");
        }
    }

    @Override
    public void deleteAttraction(AttractionDto attractionDto) {
        Attraction attraction = attractionMapper.mapToAttraction(attractionDto);
        if (attraction == null)
            throw new NoSuchAttractionException("Достопримечательности с названием " + attractionDto.name() + " не существует!");
        attractionRepository.delete(attraction);
    }
}
