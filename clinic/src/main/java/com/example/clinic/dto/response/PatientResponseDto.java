package com.example.clinic.dto.response;

import java.util.UUID;

public record PatientResponseDto(
        UUID id,
        String name,
        String email
) {}