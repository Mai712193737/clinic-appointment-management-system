package com.example.clinic.service;

import com.example.clinic.model.Doctor;
import com.example.clinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public com.example.clinic.dto.response.DoctorResponse addDoctor(com.example.clinic.dto.request.DoctorRequest requestDto) {
        if (doctorRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        Doctor doctor = new Doctor();
        doctor.setName(requestDto.name());
        doctor.setSpecialization(requestDto.specialization());
        doctor.setEmail(requestDto.email());
        doctor.setPhoneNumber(requestDto.phoneNumber());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponseDto(savedDoctor);
    }

    public List<com.example.clinic.dto.response.DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public com.example.clinic.dto.response.DoctorResponse getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return mapToResponseDto(doctor);
    }

    private com.example.clinic.dto.response.DoctorResponse mapToResponseDto(Doctor doctor) {
        return new com.example.clinic.dto.response.DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getPhoneNumber()
        );
    }
}