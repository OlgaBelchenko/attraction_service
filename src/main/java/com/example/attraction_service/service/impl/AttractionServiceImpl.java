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
import com.example.attraction_service.exception.AttractionAlreadyExistsException;
import com.example.attraction_service.exception.NoSuchAttractionException;
import com.example.attraction_service.exception.NoSuchAttractionTypeException;
import com.example.attraction_service.repository.AssistanceRepository;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import com.example.attraction_service.service.AttractionService;
import com.example.attraction_service.utils.mapper.AssistanceMapper;
import com.example.attraction_service.utils.mapper.AttractionMapper;
import com.example.attraction_service.utils.mapper.LocalityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;
    private final AssistanceRepository assistanceRepository;

    @Override
    public void addAttraction(AttractionDto attractionDto) {
        Attraction attraction;
        attraction = AttractionMapper.mapToAttraction(attractionDto);

        if (attractionRepository.existsByName(attraction.getName()))
            throw new AttractionAlreadyExistsException(String.format(ATTRACTION_ALREADY_EXISTS, attraction.getName()));

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
        Locality locality = localityRepository.findByNameAndRegion(
                localityDto.name(), localityDto.region()).orElse(null);
        if (locality == null) {
            locality = LocalityMapper.mapToLocality(localityDto);
            localityRepository.save(locality);
        }
        return locality;
    }

    @Override
    public List<AttractionDto> getAllAttractions(Optional<Boolean> sortByName, Optional<String> attractionType) {
        List<Attraction> attractions = attractionRepository.findAll();

        if (!attractions.isEmpty()) {
            if (sortByName.isPresent() && sortByName.get()) {
                attractions.sort(Comparator.comparing(Attraction::getName));
            }

            if (attractionType.isPresent()) {
                try {
                    AttractionType enumAttractionType = AttractionType.valueOf(attractionType.get().toUpperCase());
                    attractions = attractions.stream().filter(attr -> attr.getAttractionType().equals(enumAttractionType)).toList();
                } catch (IllegalArgumentException e) {
                    throw new NoSuchAttractionTypeException(String.format(NO_SUCH_ATTRACTION_TYPE, attractionType));
                }
            }
        }

        return attractions.stream().map(AttractionMapper::mapToAttractionDto).toList();
    }

    @Override
    public List<AttractionDto> getAllAttractionsByLocality(LocalityDto localityDto) {
        Locality locality = LocalityMapper.mapToLocality(localityDto);
        Locality existingLocality = localityRepository.findByNameAndRegion(locality.getName(), locality.getRegion()).orElse(null);
        if (existingLocality != null) {
            locality = existingLocality;
        } else {
            locality = localityRepository.save(locality);
        }
        return attractionRepository.findByLocality(locality).stream().map(AttractionMapper::mapToAttractionDto).toList();
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
