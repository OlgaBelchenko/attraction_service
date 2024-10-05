package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.Locality;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    boolean existsByName(String name);
    List<Attraction> findByLocality(Locality locality);
}
