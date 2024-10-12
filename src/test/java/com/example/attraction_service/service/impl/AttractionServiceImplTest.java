package com.example.attraction_service.service.impl;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.AttractionAlreadyExistsException;
import com.example.attraction_service.exception.InvalidSortParameterException;
import com.example.attraction_service.exception.NoSuchAttractionException;
import com.example.attraction_service.exception.NoSuchLocalityException;
import com.example.attraction_service.repository.AssistanceRepository;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttractionServiceImplTest {

    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private LocalityRepository localityRepository;

    @Mock
    private AssistanceRepository assistanceRepository;

    @InjectMocks
    private AttractionServiceImpl attractionService;

    @Test
    void testAddAttraction_success() {
        AttractionDto attractionDto = AttractionDto.builder()
                .name("attraction")
                .locality(new LocalityDto("locality", "region"))
                .attractionType("CASTLE")
                .build();

        attractionService.addAttraction(attractionDto);

        verify(attractionRepository, times(1)).save(any(Attraction.class));
    }

    @Test
    void testAddAttraction_alreadyExists_throwsAttractionAlreadyExistsException() {
        LocalityDto localityDto = new LocalityDto("locality", "region");
        AttractionDto attractionDto = AttractionDto.builder()
                .name("existing attraction")
                .locality(localityDto)
                .attractionType("CASTLE")
                .build();

        when(attractionRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(AttractionAlreadyExistsException.class, () -> attractionService.addAttraction(attractionDto));
    }

    @Test
    void testGetAllAttractions_attractionsExist_returnsNotEmptyPageable() {
        Attraction attraction = Attraction.builder()
                .name("name")
                .locality(Locality.builder().name("locality").region("region").build())
                .attractionType(AttractionType.CASTLE)
                .assistances(new ArrayList<>())
                .build();
        Page<Attraction> attractions = new PageImpl<>(List.of(attraction));

        when(attractionRepository.findAll(any(Pageable.class))).thenReturn(attractions);

        Page<AttractionDto> result = attractionService.getAllAttractions(Optional.empty(), Optional.empty(), Optional.of(0), Optional.of(10));

        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetAllAttractions_invalidSortParameters_throwsInvalidSortParameterException() {
        String invalidSortParameter = "invalid";

        assertThrows(InvalidSortParameterException.class, () -> attractionService.getAllAttractions(Optional.empty(), Optional.of(invalidSortParameter), Optional.empty(), Optional.empty()));
    }

    @Test
    void testGetAllAttractions_attractionsExistByAttractionType_returnsNotEmptyPageable() {
        Attraction attraction = Attraction.builder()
                .name("name")
                .locality(Locality.builder().name("locality").region("region").build())
                .attractionType(AttractionType.CASTLE)
                .assistances(new ArrayList<>())
                .build();
        Page<Attraction> attractions = new PageImpl<>(List.of(attraction));

        when(attractionRepository.findAllByAttractionType(any(AttractionType.class), any(Pageable.class))).thenReturn(attractions);

        Page<AttractionDto> result = attractionService.getAllAttractions(Optional.of("CASTLE"), Optional.empty(), Optional.empty(), Optional.empty());

        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetAllAttractionsByLocality_attractionsExistByLocality_returnsNotEmptyPageable() {
        LocalityDto localityDto = new LocalityDto("locality", "region");
        Locality locality = Locality.builder().name("locality").region("region").build();
        Attraction attraction = Attraction.builder()
                .name("name")
                .locality(Locality.builder().name("locality").region("region").build())
                .attractionType(AttractionType.CASTLE)
                .assistances(new ArrayList<>())
                .build();
        Page<Attraction> attractions = new PageImpl<>(List.of(attraction));

        when(localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(anyString(), anyString())).thenReturn(Optional.of(locality));
        when(attractionRepository.findByLocality(any(Locality.class), any(Pageable.class))).thenReturn(attractions);

        Page<AttractionDto> result = attractionService.getAllAttractionsByLocality(localityDto, Optional.empty(), Optional.empty());

        assertNotNull(result);
    }

    @Test
    void testGetAllAttractionsByLocality_localityNotFound_throwsNoSuchLocalityException() {
        LocalityDto localityDto = new LocalityDto("non-existent Locality", "non-existent Region");

        when(localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchLocalityException.class, () -> attractionService.getAllAttractionsByLocality(localityDto, Optional.empty(), Optional.empty()));
    }

    @Test
    void testUpdateAttractionDescription_success() {
        UpdateAttractionDescriptionRequest updateRequest = new UpdateAttractionDescriptionRequest("attraction", "new description");

        when(attractionRepository.findByName(anyString())).thenReturn(Optional.of(Attraction.builder().build()));

        attractionService.updateAttractionDescription(updateRequest);

        verify(attractionRepository, times(1)).save(any(Attraction.class));
    }

    @Test
    void testUpdateAttractionDescription_attractionNotFound_throwsAttractionNotFoundException() {
        UpdateAttractionDescriptionRequest updateRequest = new UpdateAttractionDescriptionRequest("non-existent Attraction", "new description");

        when(attractionRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchAttractionException.class, () -> attractionService.updateAttractionDescription(updateRequest));
    }

    @Test
    void testDeleteAttraction_success() {
        String name = "attraction";

        when(attractionRepository.findByName(anyString())).thenReturn(Optional.of(Attraction.builder().build()));

        attractionService.deleteAttraction(name);

        verify(attractionRepository, times(1)).delete(any(Attraction.class));
    }

    @Test
    void testDeleteAttraction_attractionNotFound_throwsNoSuchAttractionException() {
        String name = "non-existent attraction";

        when(attractionRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchAttractionException.class, () -> attractionService.deleteAttraction(name));
    }
}