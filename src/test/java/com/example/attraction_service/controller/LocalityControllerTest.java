package com.example.attraction_service.controller;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.exception.LocalityAlreadyExistsException;
import com.example.attraction_service.service.LocalityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.attraction_service.utils.DefaultErrorMessages.LOCALITY_ALREADY_EXISTS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocalityController.class)
@AutoConfigureMockMvc
class LocalityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocalityService localityService;


    @Test
    @SneakyThrows
    void testAddLocality_success() {
        LocalityDto localityDto = new LocalityDto("locality", "region");

        ResultActions result = mockMvc.perform(post("/api/locality/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localityDto)));

        verify(localityService, times(1)).addLocality(localityDto);
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testAddLocality_invalidInput_returnsBadRequest() {
        mockMvc.perform(post("/api/locality/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LocalityDto(null, "region"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testAddLocalityService_exception_returnsBadRequest() {
        doThrow(new LocalityAlreadyExistsException(String.format(LOCALITY_ALREADY_EXISTS, "locality")))
                .when(localityService).addLocality(any(LocalityDto.class));

        mockMvc.perform(post("/api/locality/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LocalityDto("locality", "region"))))
                .andExpect(status().isBadRequest());
    }
}