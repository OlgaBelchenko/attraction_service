package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceAttraction {

    @EmbeddedId
    private AssistanceAttractionKey assistanceAttractionKey;

    @ManyToOne
    @MapsId("assistanceId")
    @JoinColumn(name = "assistance_id")
    private Assistance assistance;

    @ManyToOne
    @MapsId("attractionId")
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
}
