package com.example.StudentsApis.repository;

import com.example.StudentsApis.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
        Optional<Patient> findByEmail(String email);
        List<Patient> findByNameContaining(String name);
        boolean existsByEmail(String email);
}