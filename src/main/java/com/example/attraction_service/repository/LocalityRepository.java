package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Locality;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface LocalityRepository extends JpaRepository<Locality, Long> {
}
