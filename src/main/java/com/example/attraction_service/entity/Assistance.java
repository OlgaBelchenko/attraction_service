package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assistance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssistanceType assistanceType;

    @Column(name = "description")
    private String description;

    @Column(name = "executor")
    private String executor;

    @ManyToMany(mappedBy = "assistances")
    private List<Attraction> attractions;
}
