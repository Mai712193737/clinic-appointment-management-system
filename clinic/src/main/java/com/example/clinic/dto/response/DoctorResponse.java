package com.example.clinic.dto.response;

import com.example.clinic.dto.WorkingPeriodDto;
import com.example.clinic.model.Specialization;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DoctorResponse(
        UUID id,
        String fullName,
        String specialization,
        String email,
        String phoneNumber,
        String medicalLicenseNumber,
        Integer experienceYears,
        BigDecimal consultationFee,
        List<WorkingPeriodDto> workingPeriods
         
) {}