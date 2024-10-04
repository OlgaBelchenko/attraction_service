package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Attraction;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
