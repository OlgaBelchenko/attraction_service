package com.example.attraction_service.controller;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.AttractionsRequest;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping("/add")
    ResponseEntity<Void> addAttraction(@RequestBody AttractionDto attractionDto) {
        attractionService.addAttraction(attractionDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/list")
    ResponseEntity<List<AttractionDto>> getAllAttractions(@RequestBody AttractionsRequest attractionsRequest) {
        return ResponseEntity.ok(attractionService.getAllAttractions(attractionsRequest));
    }

    @GetMapping("/list_by_locality")
    ResponseEntity<List<AttractionDto>> getAllLocalityAttractions(@RequestBody LocalityDto localityDto) {
        return ResponseEntity.ok(attractionService.getAllLocalityAttractions(localityDto));
    }

    @PutMapping("/update")
    ResponseEntity<Void> updateAttraction(@RequestBody AttractionDto attractionDto) {
        attractionService.updateAttraction(attractionDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteAttraction(@RequestBody AttractionDto attractionDto) {
        attractionService.deleteAttraction(attractionDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
