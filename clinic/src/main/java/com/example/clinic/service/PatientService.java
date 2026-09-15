package com.example.clinic.service;

import com.example.clinic.model.Patient;
import com.example.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public com.example.clinic.dto.response.PatientResponse addPatient(com.example.clinic.dto.request.PatientRequest requestDto) {
        if (patientRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        Patient patient = new Patient();
        patient.setFirstName(requestDto.firstName());
        patient.setLastName(requestDto.lastName());
        patient.setBirthDate(requestDto.birthDate());
        patient.setPhoneNumber(requestDto.phoneNumber());
        patient.setGender(requestDto.gender());
        patient.setEmail(requestDto.email());
        patient.setPassword(requestDto.password());

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponseDto(savedPatient);
    }

    public List<com.example.clinic.dto.response.PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public com.example.clinic.dto.response.PatientResponse getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return mapToResponseDto(patient);
    }

    public com.example.clinic.dto.response.PatientResponse updatePatient(UUID id, com.example.clinic.dto.request.PatientRequest requestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        if (!patient.getEmail().equalsIgnoreCase(requestDto.email())
                && patientRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken by another patient!");
        }

        patient.setFirstName(requestDto.firstName());
        patient.setLastName(requestDto.lastName());
        patient.setBirthDate(requestDto.birthDate());
        patient.setPhoneNumber(requestDto.phoneNumber());
        patient.setGender(requestDto.gender());
        patient.setEmail(requestDto.email());
        if (requestDto.password() != null && !requestDto.password().isBlank()) {
            patient.setPassword(requestDto.password());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return mapToResponseDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        patientRepository.delete(patient);
    }

    private com.example.clinic.dto.response.PatientResponse mapToResponseDto(Patient patient) {
        return new com.example.clinic.dto.response.PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getPhoneNumber(),
                patient.getGender(),
                patient.getRegistrationDate(),
                patient.getEmail()
        );
    }
}