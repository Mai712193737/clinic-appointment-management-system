package com.example.clinic.controller;

import com.example.clinic.dto.request.BookAppointmentRequest;
import com.example.clinic.dto.ApiResponse;
import com.example.clinic.dto.response.AppointmentResponse;
import com.example.clinic.model.enums.Role;
import com.example.clinic.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ApiResponse<AppointmentResponse> bookAppointment(
            @RequestBody BookAppointmentRequest request) {
        return appointmentService.bookAppointment(request);
    }

    @PatchMapping("/{appointmentId}/confirm")
    public ApiResponse<Void> confirmAppointment(
            @PathVariable UUID appointmentId) {
        return appointmentService.confirmAppointment(appointmentId);
    }

    // مؤقتًا requesterId و requesterRole جايين من الـ query params،
    // ولما تضيف Security/JWT خدهم من الـ token بدل كده.
    @PatchMapping("/{appointmentId}/cancel")
    public ApiResponse<Void> cancelAppointment(
            @PathVariable UUID appointmentId,
            @RequestParam UUID requesterId,
            @RequestParam Role requesterRole) {
        return appointmentService.cancelAppointment(appointmentId, requesterId, requesterRole);
    }

    @PatchMapping("/{appointmentId}/complete")
    public ApiResponse<Void> completeAppointment(
            @PathVariable UUID appointmentId) {
        return appointmentService.completeAppointment(appointmentId);
    }

    @PatchMapping("/{appointmentId}/no-show")
    public ApiResponse<Void> markAsNoShow(
            @PathVariable UUID appointmentId) {
        return appointmentService.markAsNoShow(appointmentId);
    }

    @GetMapping("/available-slots")
    public ApiResponse<List<LocalTime>> getAvailableSlots(
            @RequestParam UUID doctorId,
            @RequestParam LocalDate date) {
        return appointmentService.getAvailableSlots(doctorId, date);
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<AppointmentResponse>> getPatientAppointments(
            @PathVariable UUID patientId) {
        return appointmentService.getPatientAppointments(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<AppointmentResponse>> getDoctorAppointments(
            @PathVariable UUID doctorId) {
        return appointmentService.getDoctorAppointments(doctorId);
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}