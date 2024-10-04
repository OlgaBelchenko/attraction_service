package com.example.attraction_service.controller;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.service.LocalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locality")
public class LocalityController {

    private final LocalityService localityService;

    @PostMapping("/add")
    ResponseEntity<Void> addLocality(@RequestBody LocalityDto localityDto) {
        localityService.addLocality(localityDto);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<Void> updateAttraction(@RequestBody LocalityDto localityDto) {
        localityService.updateLocality(localityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
