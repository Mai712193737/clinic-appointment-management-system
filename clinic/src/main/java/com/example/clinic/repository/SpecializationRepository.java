package com.example.clinic.repository;

import com.example.clinic.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    boolean existsByName(String name);

    Optional<Specialization> findByName(String name);
}
