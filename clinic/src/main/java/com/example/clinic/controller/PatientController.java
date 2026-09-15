package com.example.clinic.controller;

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
    public ResponseEntity<List<com.example.clinic.dto.response.PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<com.example.clinic.dto.response.PatientResponse> createPatient(@Valid @RequestBody com.example.clinic.dto.request.PatientRequest requestDto) {
        com.example.clinic.dto.response.PatientResponse response = patientService.addPatient(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.clinic.dto.response.PatientResponse> getPatientById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }


    @PutMapping("/{id")
    public ResponseEntity<com.example.clinic.dto.response.PatientResponse> updatePatient(@PathVariable UUID id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}