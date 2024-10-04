package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.LocalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepository localityRepository;

    @Override
    public void addLocality(LocalityDto localityDto) {

    }

    @Override
    public void updateLocality(LocalityDto localityDto) {

    }
}
