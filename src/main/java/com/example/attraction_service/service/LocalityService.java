package com.example.attraction_service.service;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocalityService {
    void addLocality(LocalityDto localityDto);

    void updateLocality(LocalityDto localityDto);

}
