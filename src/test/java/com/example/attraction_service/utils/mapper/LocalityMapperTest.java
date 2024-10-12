package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalityMapperTest {

    @Test
    void mapToLocalityDto() {
        Locality locality = Locality.builder().name("locality1").region("region1").build();

        LocalityDto result = LocalityMapper.mapToLocalityDto(locality);

        assertThat(result.name()).isEqualTo("locality1");
        assertThat(result.region()).isEqualTo("region1");
    }

    @Test
    void mapToLocality() {
        LocalityDto localityDto = new LocalityDto("locality2", "region2");

        Locality result = LocalityMapper.mapToLocality(localityDto);

        assertThat(result.getName()).isEqualTo("locality2");
        assertThat(result.getRegion()).isEqualTo("region2");
    }
}