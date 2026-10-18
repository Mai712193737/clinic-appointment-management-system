
package com.example.clinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PrescriptionRequest(

        @NotBlank(message = "Medication name is required")
        @Size(max = 150)
        String medicationName,

        @NotBlank(message = "Dosage is required")
        @Size(max = 100)
        String dosage,

        @NotBlank(message = "Frequency is required")
        @Size(max = 100)
        String frequency,

        @NotBlank(message = "Duration is required")
        @Size(max = 100)
        String duration,

        @Size(max = 1000)
        String instructions

) {
}
