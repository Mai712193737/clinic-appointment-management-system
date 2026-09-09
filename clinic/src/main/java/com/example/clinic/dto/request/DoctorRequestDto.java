package com.example.clinic.dto.request;

public record DoctorRequestDto(
        String name,
        String specialization,
        String email,
        String phoneNumber
) {}