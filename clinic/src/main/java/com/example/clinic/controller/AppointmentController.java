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
            @PathVariable long appointmentId) {
        return appointmentService.confirmAppointment(appointmentId);
    }

    // مؤقتًا requesterId و requesterRole جايين من الـ query params،
    // ولما تضيف Security/JWT خدهم من الـ token بدل كده.
    @PatchMapping("/{appointmentId}/cancel")
    public ApiResponse<Void> cancelAppointment(
            @PathVariable long appointmentId,
            @RequestParam long requesterId,
            @RequestParam Role requesterRole) {
        return appointmentService.cancelAppointment(appointmentId, requesterId, requesterRole);
    }

    @PatchMapping("/{appointmentId}/complete")
    public ApiResponse<Void> completeAppointment(
            @PathVariable long appointmentId) {
        return appointmentService.completeAppointment(appointmentId);
    }

    @PatchMapping("/{appointmentId}/no-show")
    public ApiResponse<Void> markAsNoShow(
            @PathVariable long appointmentId) {
        return appointmentService.markAsNoShow(appointmentId);
    }

    @GetMapping("/available-slots")
    public ApiResponse<List<LocalTime>> getAvailableSlots(
            @RequestParam long doctorId,
            @RequestParam LocalDate date) {
        return appointmentService.getAvailableSlots(doctorId, date);
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<AppointmentResponse>> getPatientAppointments(
            @PathVariable long patientId) {
        return appointmentService.getPatientAppointments(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<AppointmentResponse>> getDoctorAppointments(
            @PathVariable long doctorId) {
        return appointmentService.getDoctorAppointments(doctorId);
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}