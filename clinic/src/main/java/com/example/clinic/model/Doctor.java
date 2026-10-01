package com.example.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    @Column(name = "medical_license_number", unique = true, nullable = false)
    private String medicalLicenseNumber;

    @Min(value = 0, message = "Experience years cannot be negative")
    private Integer experienceYears;

    private BigDecimal consultationFee;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingPeriod> workingPeriods = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, Specialization specialization,
                  String email, String phoneNumber, String medicalLicenseNumber,
                  Integer experienceYears, BigDecimal consultationFee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
    }


}