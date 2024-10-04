package com.example.attraction_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AssistanceAttractionKey implements Serializable {

    @Column(name = "assistance_id")
    protected long assistanceId;

    @Column(name = "attraction_id")
    protected long attractionId;
}
