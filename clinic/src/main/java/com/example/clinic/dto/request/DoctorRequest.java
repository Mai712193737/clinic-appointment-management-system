package com.example.clinic.dto.request;

import com.example.clinic.dto.WorkingPeriodDto;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record DoctorRequest(
        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @NotBlank(message = "Specialization cannot be empty")
        String specialization,

        @NotBlank(message = "Email cannot be empty")
        String email,

        String phoneNumber,
        
        @NotBlank(message = "Medical license number cannot be empty")
        String medicalLicenseNumber,

        @NotBlank(message = "Experience years cannot be empty")
        Integer experienceYears,

        @NotBlank(message = "Consultation fee cannot be empty")
        BigDecimal consultationFee

        
) {}
