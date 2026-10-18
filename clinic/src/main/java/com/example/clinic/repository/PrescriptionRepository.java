
package com.example.clinic.repository;

import com.example.clinic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, UUID> {

    List<Prescription> findByMedicalRecord_Id(UUID recordId);

    List<Prescription> findByMedicalRecord_Patient_Id(UUID patientId);
}
