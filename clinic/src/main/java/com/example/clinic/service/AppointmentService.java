package com.example.clinic.service;

import com.example.clinic.dto.ApiResponse;
import com.example.clinic.dto.request.BookAppointmentRequest;
import com.example.clinic.dto.response.AppointmentResponse;
import com.example.clinic.model.Appointment;
import com.example.clinic.model.Doctor;
import com.example.clinic.model.Patient;
import com.example.clinic.model.WorkingPeriod;
import com.example.clinic.model.enums.AppointmentStatus;
import com.example.clinic.model.enums.Role;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.WorkingPeriodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private static final int SLOT_MINUTES = 30;

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final WorkingPeriodRepository workingPeriodRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            WorkingPeriodRepository workingPeriodRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.workingPeriodRepository = workingPeriodRepository;
    }

    //Booking

    public ApiResponse<AppointmentResponse> bookAppointment(BookAppointmentRequest request) {
        if (request == null || request.getAppointmentDate() == null
                || request.getAppointmentTime() == null) {
            return ApiResponse.error("Appointment date and time are required");
        }

        Optional<Doctor> doctor = doctorRepository.findById(request.getDoctorId());
        if (doctor.isEmpty()) {
            return ApiResponse.error("Doctor not found");
        }

        Optional<Patient> patient = patientRepository.findById(request.getPatientId());
        if (patient.isEmpty()) {
            return ApiResponse.error("Patient not found");
        }

        LocalDate date = request.getAppointmentDate();
        LocalTime time = request.getAppointmentTime();

        // مينفعش نحجز في الماضي
        if (date.isBefore(LocalDate.now())
                || (date.equals(LocalDate.now()) && !time.isAfter(LocalTime.now()))) {
            return ApiResponse.error("Cannot book an appointment in the past");
        }

        List<LocalTime> available = computeAvailableSlots(request.getDoctorId(), date);
        if (!available.contains(time)) {
            return ApiResponse.error("This time slot is not available");
        }

        Appointment appointment = new Appointment(
                null,
                patient.get(),
                doctor.get(),
                date,
                time,
                request.getReason(),
                AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);
        return ApiResponse.success("Appointment booked successfully", toResponse(saved));
    }



    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> result = appointmentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Appointments retrieved successfully", result);
    }

    public ApiResponse<List<AppointmentResponse>> getDoctorAppointments(long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            return ApiResponse.error("Doctor not found");
        }
        List<AppointmentResponse> result = appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Doctor appointments retrieved successfully", result);
    }

    public ApiResponse<List<AppointmentResponse>> getPatientAppointments(long patientId) {
        if (!patientRepository.existsById(patientId)) {
            return ApiResponse.error("Patient not found");
        }
        List<AppointmentResponse> result = appointmentRepository.findByPatientId(patientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Patient appointments retrieved successfully", result);
    }

    public ApiResponse<List<LocalTime>> getAvailableSlots(long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            return ApiResponse.error("Doctor not found");
        }
        if (date == null) {
            return ApiResponse.error("Date is required");
        }
        return ApiResponse.success("Available slots retrieved successfully",
                computeAvailableSlots(doctorId, date));
    }



    public ApiResponse<Void> confirmAppointment(long appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.CONFIRMED, "Appointment confirmed");
    }

    public ApiResponse<Void> completeAppointment(long appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.COMPLETED, "Appointment completed");
    }

    public ApiResponse<Void> markAsNoShow(long appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.NO_SHOW, "Appointment marked as no-show");
    }

    private ApiResponse<Void> cancelAppointment(long appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.CANCELLED, "Appointment cancelled");
    }

    public ApiResponse<Void> cancelAppointment(long appointmentId, long requesterId, Role requesterRole) {
        Optional<Appointment> found = appointmentRepository.findById(appointmentId);
        if (found.isEmpty()) {
            return ApiResponse.error("Appointment not found");
        }
        Appointment appointment = found.get();

        boolean allowed = switch (requesterRole) {
            case ADMIN -> true;
            case DOCTOR -> appointment.getDoctor().getId() == requesterId;
            case PATIENT -> appointment.getPatient().getId() == requesterId;
            default -> false;
        };
        if (!allowed) {
            return ApiResponse.error("You are not allowed to cancel this appointment");
        }
        return cancelAppointment(appointmentId);
    }

    public boolean isValidStatusTransition(AppointmentStatus current, AppointmentStatus newStatus) {
        if (current == null || newStatus == null) {
            return false;
        }
        return switch (current) {
            case PENDING -> newStatus == AppointmentStatus.CONFIRMED
                    || newStatus == AppointmentStatus.CANCELLED;
            case CONFIRMED -> newStatus == AppointmentStatus.COMPLETED
                    || newStatus == AppointmentStatus.NO_SHOW
                    || newStatus == AppointmentStatus.CANCELLED;
            default -> false;
        };
    }

    private ApiResponse<Void> changeStatus(long appointmentId, AppointmentStatus newStatus, String successMessage) {
        Optional<Appointment> found = appointmentRepository.findById(appointmentId);
        if (found.isEmpty()) {
            return ApiResponse.error("Appointment not found");
        }

        Appointment appointment = found.get();
        if (!isValidStatusTransition(appointment.getStatus(), newStatus)) {
            return ApiResponse.error("Cannot change status from "
                    + appointment.getStatus() + " to " + newStatus);
        }

        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);
        return ApiResponse.success(successMessage, null);
    }

    private List<LocalTime> computeAvailableSlots(long doctorId, LocalDate date) {
        List<LocalTime> slots = new ArrayList<>();

        if (date.isBefore(LocalDate.now())) {
            return slots;
        }

        List<WorkingPeriod> periods =
                workingPeriodRepository.findByDoctorIdAndDayOfWeek(doctorId, date.getDayOfWeek());

        Set<LocalTime> booked = appointmentRepository.findByDoctorIdAndDate(doctorId, date).stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                .map(Appointment::getTime)
                .collect(Collectors.toSet());

        LocalTime now = LocalTime.now();
        boolean today = date.equals(LocalDate.now());

        for (WorkingPeriod period : periods) {
            LocalTime slot = period.getStartTime();

            while (!slot.plusMinutes(SLOT_MINUTES).isAfter(period.getEndTime())) {
                boolean isPast = today && !slot.isAfter(now);
                if (!booked.contains(slot) && !isPast) {
                    slots.add(slot);
                }
                LocalTime next = slot.plusMinutes(SLOT_MINUTES);
                if (next.isBefore(slot)) { // عدّينا منتصف الليل
                    break;
                }
                slot = next;
            }
        }

        slots.sort(LocalTime::compareTo);
        return slots;
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getPatient().getId(),
                a.getDoctor().getId(),
                a.getDate(),
                a.getTime(),
                a.getReason(),
                a.getStatus());
    }
}