package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.AttractionDto;
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
import com.example.attraction_service.utils.mapper.AttractionMapper;
import com.example.attraction_service.utils.mapper.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.example.attraction_service.utils.DefaultErrorMessages.*;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;

    @Override
    public void addAttraction(AttractionDto attractionDto) {
        Attraction attraction = AttractionMapper.mapToAttraction(attractionDto);
        if (attractionRepository.existsByName(attraction.getName())) return;
        attractionRepository.save(attraction);
    }

    @Override
    public List<AttractionDto> getAllAttractions(boolean sortByName, String attractionTypeName) {
        List<Attraction> attractions = attractionRepository.findAll();

        if (!attractions.isEmpty()) {
            if (sortByName) {
                attractions.sort(Comparator.comparing(Attraction::getName));
            }

            if (attractionTypeName != null) {
                try {
                    AttractionType attractionType = AttractionType.valueOf(attractionTypeName);
                    attractions = attractions.stream().filter(attr -> attr.getAttractionType().equals(attractionType)).toList();
                } catch (IllegalArgumentException e) {
                    throw new NoSuchAttractionTypeException(String.format(NO_SUCH_ATTRACTION_TYPE, attractionTypeName));
                }
            }
        }

        return attractions.stream().map(AttractionMapper::mapToAttractionDto).toList();
    }

    @Override
    public List<AttractionDto> getAllLocalityAttractions(LocalityDto localityDto) {
        Locality locality = LocalityMapper.mapToLocality(localityDto);
        List<AttractionDto> attractionDtos;
        if (localityRepository.existsByName(locality.getName())) {
            attractionDtos = attractionRepository.findByLocality(locality).stream().map(AttractionMapper::mapToAttractionDto).toList();
        } else {
            throw new NoSuchLocalityException(String.format(NO_SUCH_LOCALITY, locality.getName()));
        }
        return attractionDtos;
    }

    @Override
    public void updateAttraction(AttractionDto attractionDto) {
        Attraction attraction = AttractionMapper.mapToAttraction(attractionDto);
        if (attractionRepository.existsByName(attraction.getName())) {
            attractionRepository.save(attraction);
        } else {
            throw new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, attractionDto.name()));
        }
    }

    @Override
    public void deleteAttraction(AttractionDto attractionDto) {
        Attraction attraction = AttractionMapper.mapToAttraction(attractionDto);
        if (attraction == null)
            throw new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, attractionDto.name()));
        attractionRepository.delete(attraction);
    }
}
