package com.example.clinic.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class BookAppointmentRequest {

    private UUID patientId;
    private UUID doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;

    public BookAppointmentRequest() {
    }

    public BookAppointmentRequest(UUID patientId,
                                  UUID doctorId,
                                  LocalDate appointmentDate,
                                  LocalTime appointmentTime,
                                  String reason) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}