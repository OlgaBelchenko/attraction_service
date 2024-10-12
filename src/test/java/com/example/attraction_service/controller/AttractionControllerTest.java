package com.example.attraction_service.controller;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.exception.NoSuchAttractionException;
import com.example.attraction_service.service.AttractionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static com.example.attraction_service.utils.DefaultErrorMessages.NO_SUCH_ATTRACTION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttractionController.class)
@AutoConfigureMockMvc
class AttractionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AttractionService attractionService;

    @Test
    @SneakyThrows
    void testAddAttraction_success() {
        AttractionDto attractionDto = AttractionDto.builder()
                .name("attraction")
                .attractionType("CASTLE")
                .locality(new LocalityDto("locality", "region"))
                .build();
        mockMvc.perform(post("/api/attraction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attractionDto)))
                .andExpect(status().isOk());

        verify(attractionService, times(1)).addAttraction(attractionDto);
    }

    @Test
    @SneakyThrows
    void testAddAttraction_invalidInput_returnsBadRequest() {
        AttractionDto attractionDto = AttractionDto.builder()
                .name(null)
                .attractionType("CASTLE")
                .build();

        mockMvc.perform(post("/api/attraction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attractionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testGetAllAttractions_success() {
        Page<AttractionDto> attractions = new PageImpl<>(Arrays.asList(
                AttractionDto.builder().name("attraction 1").attractionType("CASTLE").build(),
                AttractionDto.builder().name("attraction 2").attractionType("PARK").build()
        ));

        when(attractionService.getAllAttractions(any(), any(), any(), any())).thenReturn(attractions);
        mockMvc.perform(get("/api/attraction/list")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2));
    }

    @Test
    @SneakyThrows
    void testGetAllAttractionsByLocality_success() {
        String region = "region";
        String name = "locality";
        Page<AttractionDto> attractions = new PageImpl<>(Arrays.asList(
                AttractionDto.builder().name("Attraction 1").attractionType("CASTLE").build(),
                AttractionDto.builder().name("Attraction 2").attractionType("PARK").build()
        ));
        when(attractionService.getAllAttractionsByLocality(any(), any(), any())).thenReturn(attractions);

        mockMvc.perform(get("/api/attraction/list/locality")
                        .param("region", region)
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2));
    }

    @Test
    @SneakyThrows
    void testUpdateAttraction_success() {
        UpdateAttractionDescriptionRequest updateRequest = new UpdateAttractionDescriptionRequest("attraction", "new description");

        mockMvc.perform(put("/api/attraction/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        verify(attractionService, times(1)).updateAttractionDescription(updateRequest);
    }

    @Test
    @SneakyThrows
    void testUpdateAttraction_notFound_returnsNotFound() {
        UpdateAttractionDescriptionRequest updateRequest = new UpdateAttractionDescriptionRequest("non-existent attraction", "new description");
        doThrow(new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, updateRequest)))
                .when(attractionService).updateAttractionDescription(any());

        mockMvc.perform(put("/api/attraction/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testDeleteAttraction_success() {
        String attractionName = "attraction";

        mockMvc.perform(delete("/api/attraction/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(attractionName))
                .andExpect(status().isNoContent());

        verify(attractionService, times(1)).deleteAttraction(attractionName);
    }

    @Test
    @SneakyThrows
    void testDeleteAttraction_notFound_returnsNotFound() {
        String attractionName = "non-existent attraction";
        doThrow(new NoSuchAttractionException(String.format(NO_SUCH_ATTRACTION, attractionName)))
                .when(attractionService).deleteAttraction(any(String.class));

        mockMvc.perform(delete("/api/attraction/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(attractionName))
                .andExpect(status().isNotFound());

    }
}
