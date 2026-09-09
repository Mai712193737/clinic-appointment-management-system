package com.example.clinic.service;

import com.example.clinic.dto.request.DoctorRequestDto;
import com.example.clinic.dto.response.DoctorResponseDto;
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

    public DoctorResponseDto addDoctor(DoctorRequestDto requestDto) {
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

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public DoctorResponseDto getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return mapToResponseDto(doctor);
    }

    private DoctorResponseDto mapToResponseDto(Doctor doctor) {
        return new DoctorResponseDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getPhoneNumber()
        );
    }
}