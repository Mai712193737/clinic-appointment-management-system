package com.example.clinic.model;

import com.example.clinic.model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private AppointmentStatus status;

    public Appointment(
            Long id,
            Patient patient,
            Doctor doctor,
            LocalDate date,
            LocalTime time,
            String reason,
            AppointmentStatus status
    ) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.status = status;
    }
}