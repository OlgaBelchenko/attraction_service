package com.example.attraction_service.service.impl;

import com.example.attraction_service.controller.LocalityController;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.LocalityAlreadyExistsException;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.LocalityService;
import com.example.attraction_service.utils.mapper.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.attraction_service.utils.DefaultErrorMessages.LOCALITY_ALREADY_EXISTS;

/**
 * Реализация интерфейса {@link LocalityService} для получения данных от {@link LocalityController}
 * и действий с сущностью Местоположение ({@link Locality}).
 */

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepository localityRepository;

    @Override
    public void addLocality(LocalityDto localityDto) {
        Locality locality = localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(localityDto.name(), localityDto.region()).orElse(null);
        if (locality != null) {
            throw new LocalityAlreadyExistsException(String.format(LOCALITY_ALREADY_EXISTS, locality.getName()));
        }
        locality = LocalityMapper.mapToLocality(localityDto);
        localityRepository.save(locality);
    }
}
