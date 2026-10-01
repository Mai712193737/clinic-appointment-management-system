package com.example.clinic.controller;

import com.example.clinic.dto.WorkingPeriodDto;
import com.example.clinic.dto.response.DoctorResponse;
import com.example.clinic.model.Doctor;
import com.example.clinic.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.clinic.dto.request.DoctorRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {this.doctorService = doctorService;}

    @GetMapping
    public ResponseEntity<List<com.example.clinic.dto.response.DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping
    public ResponseEntity<com.example.clinic.dto.response.DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest requestDto) {
        com.example.clinic.dto.response.DoctorResponse response = doctorService.addDoctor(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.clinic.dto.response.DoctorResponse> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PutMapping("/{id")
    public ResponseEntity<com.example.clinic.dto.response.DoctorResponse> updateDoctor(@PathVariable UUID id,@Valid @RequestBody DoctorRequest requestDto){
        return ResponseEntity.ok(doctorService.updateDoctorById(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{doctorId}/working-periods")
    public ResponseEntity<DoctorResponse> setWorkingPeriods(
            @PathVariable UUID doctorId,
            @Valid @RequestBody List<WorkingPeriodDto> workingPeriods) {

        return ResponseEntity.ok(doctorService.setDoctorWorkingPeriods(doctorId, workingPeriods));
    }

    @PostMapping("/{doctorId}/working-periods")
    public ResponseEntity<DoctorResponse> addWorkingPeriod(
            @PathVariable UUID doctorId,
            @Valid @RequestBody WorkingPeriodDto periodDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.addWorkingPeriod(doctorId, periodDto));
    }

    @DeleteMapping("/{doctorId}/working-periods/{periodId}")
    public ResponseEntity<DoctorResponse> removeWorkingPeriod(
            @PathVariable UUID doctorId,
            @PathVariable UUID periodId) {

        return ResponseEntity.ok(doctorService.removeWorkingPeriod(doctorId, periodId));
    }

    @GetMapping("/{doctorId}/working-periods")
    public ResponseEntity<List<WorkingPeriodDto>> getWorkingPeriods(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorWorkingPeriods(doctorId));
    }
}
