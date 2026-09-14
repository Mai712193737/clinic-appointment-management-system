package com.example.clinic.controller;

import com.example.clinic.dto.request.PatientRequestDto;
import com.example.clinic.dto.response.PatientResponseDto;
import com.example.clinic.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto requestDto) {
        PatientResponseDto response = patientService.addPatient(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientResponseDto> deletePatient(@PathVariable UUID id) {
        return ResponseEntity.accepted().build();
    }

}