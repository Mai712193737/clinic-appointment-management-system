package com.example.clinic.service;

import com.example.clinic.dto.request.PatientRequestDto;
import com.example.clinic.dto.response.PatientResponseDto;
import com.example.clinic.model.Patient;
import com.example.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository PatientRepository;

    public PatientService(PatientRepository PatientRepository) {
        this.PatientRepository = PatientRepository;
    }

    public PatientResponseDto addPatient(PatientRequestDto requestDto) {
        if (PatientRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        Patient Patient = new Patient();
        Patient.setName(requestDto.name());
        Patient.setEmail(requestDto.email());
        Patient.setPassword(requestDto.password());

        Patient savedPatient = PatientRepository.save(Patient);

        return mapToResponseDto(savedPatient);
    }
    public List<PatientResponseDto> getAllPatients() {
        return PatientRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }
    public PatientResponseDto getPatientById(UUID id) {
        Patient Patient = PatientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return mapToResponseDto(Patient);
    }
    private PatientResponseDto mapToResponseDto(Patient Patient) {
        return new PatientResponseDto(Patient.getId(), Patient.getName(), Patient.getEmail());
    }
}