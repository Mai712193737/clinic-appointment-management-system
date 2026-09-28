package com.example.clinic.repository;

import com.example.clinic.model.WorkingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkingPeriodRepository extends JpaRepository<WorkingPeriod, UUID> {
    List<WorkingPeriod> findByDoctorId(UUID doctorId);
    void deleteByDoctorId(UUID doctorId);
}