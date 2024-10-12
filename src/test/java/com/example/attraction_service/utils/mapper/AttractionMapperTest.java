package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.entity.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AttractionMapperTest {

    private final AttractionType attractionType = AttractionType.CASTLE;
    private final Date createdDate = new Date();

    @Test
    void testMapToAttractionDto() {
        Locality locality = Locality.builder().name("locality").region("region").build();
        List<Assistance> assistanceList = List.of(Assistance.builder().assistanceType(AssistanceType.CATERING).executor("executor").build());
        Attraction attraction = Attraction.builder()
                .attractionType(attractionType)
                .created(createdDate)
                .name("name1")
                .description("description1")
                .locality(locality)
                .assistances(assistanceList)
                .build();

        AttractionDto result = AttractionMapper.mapToAttractionDto(attraction);

        assertThat(result.attractionType()).isEqualTo(attractionType.toString());
        assertThat(result.created()).isEqualTo(createdDate);
        assertThat(result.name()).isEqualTo("name1");
        assertThat(result.locality().name()).isEqualTo(locality.getName());
        assertThat(result.locality().region()).isEqualTo(locality.getRegion());
        assertThat(result.description()).isEqualTo("description1");
        assertThat(result.assistanceDtos()).hasSize(1);
    }

    @Test
    void testMapToAttraction() {
        AttractionDto attractionDto = AttractionDto.builder()
                .attractionType(attractionType.toString())
                .created(createdDate)
                .name("name2")
                .description("description2")
                .build();

        Attraction result = AttractionMapper.mapToAttraction(attractionDto);

        assertThat(result.getCreated()).isEqualTo(createdDate);
        assertThat(result.getName()).isEqualTo("name2");
        assertThat(result.getDescription()).isEqualTo("description2");
    }
}