package com.example.clinic.repository;

import com.example.clinic.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, UUID> {

    Optional<MedicalRecord> findByPatient_Id(UUID patientId);

    boolean existsByPatient_Id(UUID patientId);
}