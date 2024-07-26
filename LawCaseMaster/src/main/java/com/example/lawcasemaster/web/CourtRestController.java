package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.CourtRequestDTO;
import com.example.lawcasemaster.model.dto.CourtResponseDTO;
import com.example.lawcasemaster.model.entity.Court;
import com.example.lawcasemaster.service.CourtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtRestController {

    private final CourtService courtService;

    public CourtRestController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public List<CourtResponseDTO> getAllCourts() {
        return courtService.getAllCourts();
    }

    @PostMapping
    public ResponseEntity<CourtResponseDTO> addCourt(@RequestBody CourtRequestDTO courtRequestDTO) {
        CourtResponseDTO newCourt = courtService.addCourt(courtRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return ResponseEntity.noContent().build();
    }

}