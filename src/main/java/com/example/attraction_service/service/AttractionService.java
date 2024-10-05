package com.example.attraction_service.service;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttractionService {
    void addAttraction(AttractionDto attractionDto);

    List<AttractionDto> getAllAttractions(boolean sortByName, String attractionTypeName);

    List<AttractionDto> getAllLocalityAttractions(LocalityDto localityDto);

    void updateAttraction(AttractionDto attractionDto);

    void deleteAttraction(AttractionDto attractionDto);
}
