package com.example.clinic.dto.request;

public record DoctorRequest(
        String name,
        String specialization,
        String email,
        String phoneNumber
) {}
