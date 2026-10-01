package com.example.clinic.dto;

import com.example.clinic.model.enums.Day;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

public record WorkingPeriodDto(
        UUID id,

        @NotNull(message = "Day of week is required")
        Day dayOfWeek,

        @NotNull(message = "Starting time is required")
        LocalTime startingTime,

        @NotNull(message = "Ending time is required")
        LocalTime endingTime
) {}