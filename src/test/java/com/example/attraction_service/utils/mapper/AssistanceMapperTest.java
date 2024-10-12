package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.AssistanceDto;
import com.example.attraction_service.entity.Assistance;
import com.example.attraction_service.entity.AssistanceType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssistanceMapperTest {

    private final AssistanceType assistanceType = AssistanceType.CATERING;

    @Test
    void testMapToAssistanceDto() {
        Assistance assistance = Assistance.builder()
                .assistanceType(assistanceType)
                .description("Description1")
                .executor("Executor1")
                .build();

        AssistanceDto result = AssistanceMapper.mapToAssistanceDto(assistance);

        assertThat(result.assistanceType()).isEqualTo(assistanceType);
        assertThat(result.description()).isEqualTo("Description1");
        assertThat(result.executor()).isEqualTo("Executor1");
    }

    @Test
    void testMapToAssistance() {
        AssistanceDto assistanceDto = AssistanceDto.builder()
                .assistanceType(assistanceType)
                .description("Description2")
                .executor("Executor2")
                .build();

        Assistance result = AssistanceMapper.mapToAssistance(assistanceDto);

        assertThat(result.getAssistanceType()).isEqualTo(assistanceType);
        assertThat(result.getDescription()).isEqualTo("Description2");
        assertThat(result.getExecutor()).isEqualTo("Executor2");
    }
}