package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.AttractionsRequest;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;


    @Override
    public void addAttraction(AttractionDto attractionDto) {
        // TODO document why this method is empty
    }

    @Override
    public List<AttractionDto> getAllAttractions(AttractionsRequest attractionsRequest) {
        return List.of();
    }

    @Override
    public List<AttractionDto> getAllLocalityAttractions(LocalityDto localityDto) {
        return List.of();
    }

    @Override
    public void updateAttraction(AttractionDto attractionDto) {
        // TODO document why this method is empty
    }

    @Override
    public void deleteAttraction(AttractionDto attractionDto) {
        // TODO document why this method is empty
    }
}
