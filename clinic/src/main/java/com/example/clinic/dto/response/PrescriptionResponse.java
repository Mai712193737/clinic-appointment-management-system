package com.example.clinic.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrescriptionResponse(
        UUID id,
        UUID medicalRecordId,
        String medicationName,
        String dosage,
        String frequency,
        String duration,
        String instructions,
        LocalDateTime prescribedAt
) {
}