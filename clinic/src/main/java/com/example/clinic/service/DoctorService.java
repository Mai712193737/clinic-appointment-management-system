package com.example.clinic.service;

import com.example.clinic.dto.WorkingPeriodDto;
import com.example.clinic.dto.request.DoctorRequest;
import com.example.clinic.dto.response.DoctorResponse;
import com.example.clinic.model.Doctor;
import com.example.clinic.model.Patient;
import com.example.clinic.model.Specialization;
import com.example.clinic.model.WorkingPeriod;
import com.example.clinic.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.clinic.repository.SpecializationRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationService specializationService;

    public DoctorService(DoctorRepository doctorRepository, SpecializationService specializationService) {
        this.doctorRepository = doctorRepository;
        this.specializationService = specializationService;
    }

    public DoctorResponse addDoctor(DoctorRequest requestDto) {
        if (doctorRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        Doctor doctor = new Doctor();

        doctor.setFirstName(requestDto.firstName());
        doctor.setLastName(requestDto.lastName());
        Specialization specialization = specializationService.getSpecializationByName(requestDto.specialization());
        doctor.setSpecialization(specialization);
        doctor.setEmail(requestDto.email());
        doctor.setPhoneNumber(requestDto.phoneNumber());
        doctor.setMedicalLicenseNumber(requestDto.medicalLicenseNumber());
        doctor.setExperienceYears(requestDto.experienceYears());
        doctor.setConsultationFee(requestDto.consultationFee());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponseDto(savedDoctor);
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public DoctorResponse getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return mapToResponseDto(doctor);
    }

    public DoctorResponse updateDoctorById(UUID id,DoctorRequest requestDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        if (doctorRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        Specialization specialization = specializationService.getSpecializationByName(requestDto.specialization());
        doctor.setSpecialization(specialization);
        doctor.setFirstName(requestDto.firstName());
        doctor.setLastName(requestDto.lastName());
        doctor.setEmail(requestDto.email());
        doctor.setPhoneNumber(requestDto.phoneNumber());
        doctor.setMedicalLicenseNumber(requestDto.medicalLicenseNumber());
        doctor.setExperienceYears(requestDto.experienceYears());
        doctor.setConsultationFee(requestDto.consultationFee());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponseDto(savedDoctor);
    }

    public void deleteDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }

    @Transactional
    public DoctorResponse setDoctorWorkingPeriods(UUID doctorId, List<WorkingPeriodDto> workingPeriods) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        doctor.getWorkingPeriods().clear();

        for (WorkingPeriodDto dto : workingPeriods) {
            if (dto.startingTime().isAfter(dto.endingTime()) || dto.startingTime().equals(dto.endingTime())) {
                throw new IllegalArgumentException("Starting time must be before ending time");
            }
            WorkingPeriod period = new WorkingPeriod();
            period.setDoctor(doctor);
            period.setDayOfWeek(dto.dayOfWeek());
            period.setStartingTime(dto.startingTime());
            period.setEndingTime(dto.endingTime());

            doctor.getWorkingPeriods().add(period);
        }

        return mapToResponseDto(doctorRepository.save(doctor));
    }

    @Transactional
    public DoctorResponse addWorkingPeriod(UUID doctorId, WorkingPeriodDto periodDto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        if (periodDto.startingTime().isAfter(periodDto.endingTime()) || periodDto.startingTime().equals(periodDto.endingTime())) {
            throw new IllegalArgumentException("Starting time must be before ending time");
        }

        WorkingPeriod period = new WorkingPeriod();
        period.setDoctor(doctor);
        period.setDayOfWeek(periodDto.dayOfWeek());
        period.setStartingTime(periodDto.startingTime());
        period.setEndingTime(periodDto.endingTime());

        doctor.getWorkingPeriods().add(period);

        return mapToResponseDto(doctorRepository.save(doctor));
    }

    @Transactional
    public DoctorResponse removeWorkingPeriod(UUID doctorId, UUID periodId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        boolean isRemoved = doctor.getWorkingPeriods().removeIf(wp -> wp.getId().equals(periodId));

        if (!isRemoved) {
            throw new RuntimeException("Working period not found for this doctor");
        }

        return mapToResponseDto(doctorRepository.save(doctor));
    }

    public List<WorkingPeriodDto> getDoctorWorkingPeriods(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        if (doctor.getWorkingPeriods() == null) {
            return List.of();
        }

        return doctor.getWorkingPeriods().stream()
                .map(wp -> new WorkingPeriodDto(
                        wp.getId(),
                        wp.getDayOfWeek(),
                        wp.getStartingTime(),
                        wp.getEndingTime()
                ))
                .toList();
    }

    private DoctorResponse mapToResponseDto(Doctor doctor) {
        List<WorkingPeriodDto> workingPeriods = doctor.getWorkingPeriods() == null ? List.of() :
                doctor.getWorkingPeriods().stream()
                        .map(wp -> new WorkingPeriodDto(
                                wp.getId(),
                                wp.getDayOfWeek(),
                                wp.getStartingTime(),
                                wp.getEndingTime()
                        ))
                        .toList();
        return new DoctorResponse(
                doctor.getId(),
                (doctor.getFirstName()+ doctor.getLastName()),
                doctor.getSpecialization().getName(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                doctor.getMedicalLicenseNumber(),
                doctor.getExperienceYears(),
                doctor.getConsultationFee(),
                workingPeriods
        );
    }
}