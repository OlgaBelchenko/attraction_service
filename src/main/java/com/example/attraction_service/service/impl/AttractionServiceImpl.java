package com.example.attraction_service.service.impl;

import com.example.attraction_service.controller.AttractionController;
import com.example.attraction_service.dto.AssistanceDto;
import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.entity.Assistance;
import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.exception.*;
import com.example.attraction_service.repository.AssistanceRepository;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.AttractionService;
import com.example.attraction_service.utils.mapper.AssistanceMapper;
import com.example.attraction_service.utils.mapper.AttractionMapper;
import com.example.attraction_service.utils.mapper.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.attraction_service.utils.DefaultErrorMessages.*;

/**
 * Реализация интерфейса {@link AttractionService} для получения данных от {@link AttractionController}
 * и действий с сущностью Достопримечательность ({@link Attraction}).
 */

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    public static final String DEFAULT_SORT_PARAMETER = "name";
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;
    private final AssistanceRepository assistanceRepository;

    @Override
    public void addAttraction(AttractionDto attractionDto) {
        Attraction attraction;
        AttractionType attractionType = getAttractionType(Optional.of(attractionDto.attractionType()));
        if (attractionType == null) {
            throw new NoSuchAttractionTypeException(String.format(NO_SUCH_ATTRACTION_TYPE, "null"));
        }

        attraction = AttractionMapper.mapToAttraction(attractionDto);

        if (attractionRepository.existsByName(attraction.getName()))
            throw new AttractionAlreadyExistsException(String.format(ATTRACTION_ALREADY_EXISTS, attractionDto.name()));

        attraction.setLocality(getLocality(attractionDto.locality()));

        List<AssistanceDto> assistanceDtos = attractionDto.assistanceDtos();
        if (assistanceDtos != null && !assistanceDtos.isEmpty()) {
            List<Assistance> currentAssistances = getAssistances(attraction, assistanceDtos);
            attraction.setAssistances(currentAssistances);
        }

        attractionRepository.save(attraction);
    }

    private List<Assistance> getAssistances(Attraction attraction, List<AssistanceDto> assistanceDtos) {
        List<Assistance> currentAssistances = attraction.getAssistances() == null ? new ArrayList<>() : attraction.getAssistances();
        for (AssistanceDto assistanceDto : assistanceDtos) {
            Assistance assistance = AssistanceMapper.mapToAssistance(assistanceDto);
            assistanceRepository
                    .findByAssistanceTypeAndExecutor(assistance.getAssistanceType(), assistance.getExecutor())
                    .ifPresentOrElse(currentAssistances::add,
                            () -> {
                                assistanceRepository.save(assistance);
                                currentAssistances.add(assistance);
                            });
        }
        return currentAssistances;
    }

    private Locality getLocality(LocalityDto localityDto) {
        Locality locality = localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(
                localityDto.name(), localityDto.region()).orElse(null);
        if (locality == null) {
            locality = LocalityMapper.mapToLocality(localityDto);
            localityRepository.save(locality);
        }
        return locality;
    }

    @Override
    public Page<AttractionDto> getAllAttractions(
            Optional<String> attractionType,
            Optional<String> sort,
            Optional<Integer> page,
            Optional<Integer> size) {

        Pageable pageable = getPageable(sort, page, size);

        Page<Attraction> attractions;
        AttractionType enumAttractionType = getAttractionType(attractionType);
        if (enumAttractionType != null) {
            attractions = attractionRepository.findAllByAttractionType(enumAttractionType, pageable);
        } else {
            attractions = attractionRepository.findAll(pageable);
        }

        return attractions.map(AttractionMapper::mapToAttractionDto);
    }

    private Pageable getPageable(Optional<String> sort, Optional<Integer> page, Optional<Integer> size) {
        int pageNumber = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        Sort actualSort;
        if (sort.isPresent()) {
            if (isValidSortField(sort.get())) {
                actualSort = Sort.by(sort.get());
            } else {
                throw new InvalidSortParameterException(String.format(INVALID_SORT_PARAMETER, sort.get()));
            }
        } else {
            actualSort = Sort.by(DEFAULT_SORT_PARAMETER);
        }

        return PageRequest.of(pageNumber, pageSize, actualSort);
    }

    private Pageable getPageable(Optional<Integer> page, Optional<Integer> size) {
        int pageNumber = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        return PageRequest.of(pageNumber, pageSize);
    }

    private boolean isValidSortField(String sort) {
        Class<Attraction> entityClass = Attraction.class;
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .anyMatch(field -> field.getName().equals(sort));
    }

    private AttractionType getAttractionType(Optional<String> attractionType) {
        if (attractionType.isPresent()) {
            try {
                return AttractionType.valueOf(attractionType.get().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new NoSuchAttractionTypeException(String.format(NO_SUCH_ATTRACTION_TYPE, attractionType));
            }
        }
        return null;
    }

    @Override
    public Page<AttractionDto> getAllAttractionsByLocality(LocalityDto localityDto, Optional<Integer> page, Optional<Integer> size) {
        Locality locality = LocalityMapper.mapToLocality(localityDto);
        locality = localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(locality.getName(), locality.getRegion()).orElseThrow(
                () -> new NoSuchLocalityException(String.format(NO_SUCH_LOCALITY, localityDto.region() + ", " + localityDto.name()))
        );

        return attractionRepository.findByLocality(locality, getPageable(page, size)).map(AttractionMapper::mapToAttractionDto);
    }

    @Override
    public void updateAttractionDescription(UpdateAttractionDescriptionRequest updateRequest) {
        Attraction attraction = attractionRepository.findByName(updateRequest.name()).orElseThrow(
                () -> new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, updateRequest.name())));
        attraction.setDescription(updateRequest.description());
        attractionRepository.save(attraction);
    }

    @Override
    public void deleteAttraction(String name) {
        Attraction attraction = attractionRepository.findByName(name).orElseThrow(
                () -> new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, name)));
        attractionRepository.delete(attraction);
    }
}
