package com.example.clinic.dto.response;

import java.util.UUID;

public record DoctorResponseDto(
        UUID id,
        String name,
        String specialization,
        String email,
        String phoneNumber
) {}