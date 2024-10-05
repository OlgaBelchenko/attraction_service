package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.NoSuchLocalityException;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.LocalityService;
import com.example.attraction_service.utils.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepository localityRepository;
    private final LocalityMapper localityMapper;
    private final AttractionRepository attractionRepository;

    @Override
    public void addLocality(LocalityDto localityDto) {
        Locality locality = localityMapper.mapToLocality(localityDto);
        if (localityRepository.existsByName(locality.getName())) return;
        localityRepository.save(locality);
    }

    @Override
    public void updateLocality(LocalityDto localityDto) {
        Locality locality = localityMapper.mapToLocality(localityDto);
        if (localityRepository.existsByName(locality.getName())) {
            localityRepository.save(locality);
        } else {
            throw new NoSuchLocalityException("Достопримечательности с названием " + localityDto.name() + " не существует!");
        }
    }

}
