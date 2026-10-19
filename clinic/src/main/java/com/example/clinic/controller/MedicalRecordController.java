package com.example.clinic.controller;

import com.example.clinic.dto.request.MedicalRecordRequest;
import com.example.clinic.dto.response.MedicalRecordResponse;
import com.example.clinic.service.MedicalRecordService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(
            MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        MedicalRecordResponse response =
                medicalRecordService.createMedicalRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(
            @PathVariable UUID recordId) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordById(recordId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordByPatientId(
            @PathVariable UUID patientId) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordByPatientId(patientId)
        );
    }
}