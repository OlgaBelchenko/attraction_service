package com.example.attraction_service.service;

import com.example.attraction_service.dto.LocalityDto;
import org.springframework.stereotype.Service;

@Service
public interface LocalityService {
    void addLocality(LocalityDto localityDto);

    void updateLocality(LocalityDto localityDto);
}
