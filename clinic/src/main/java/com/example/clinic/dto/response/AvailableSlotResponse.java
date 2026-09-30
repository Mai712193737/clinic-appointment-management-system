package com.example.clinic.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailableSlotResponse {

    private long doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public AvailableSlotResponse() {
    }

    public AvailableSlotResponse(long doctorId,
                                 LocalDate date,
                                 LocalTime startTime,
                                 LocalTime endTime) {
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}