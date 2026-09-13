package com.example.clinic.dto.response;

import com.example.clinic.model.enums.Gender;
import java.time.LocalDateTime;
import java.util.UUID;

public record PatientResponseDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDateTime birthDate,
        String phoneNumber,
        Gender gender,
        LocalDateTime registrationDate,
        String email
) {}