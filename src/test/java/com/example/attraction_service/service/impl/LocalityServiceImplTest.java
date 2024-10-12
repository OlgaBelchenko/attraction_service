package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.LocalityAlreadyExistsException;
import com.example.attraction_service.repository.LocalityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalityServiceImplTest {

    @Mock
    private LocalityRepository localityRepository;

    @InjectMocks
    private LocalityServiceImpl localityService;


    @Test
    void testAddLocality_success() {
        LocalityDto localityDto = new LocalityDto("new locality", "new region");

        localityService.addLocality(localityDto);

        verify(localityRepository, times(1)).save(any(Locality.class));
    }

    @Test
    void testAddLocality_alreadyExists_throwsLocalityAlreadyExistsException() {
        LocalityDto localityDto = new LocalityDto("existing locality", "existing region");
        Locality existingLocality = Locality.builder()
                .name("existing locality")
                .region("existing region")
                .build();

        when(localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(existingLocality));

        assertThrows(LocalityAlreadyExistsException.class, () -> localityService.addLocality(localityDto));
    }
}