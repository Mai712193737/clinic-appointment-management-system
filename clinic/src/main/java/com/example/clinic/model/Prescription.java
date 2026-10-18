
package com.example.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    @NotBlank(message = "Medication name is required")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String medicationName;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String frequency;

    @NotBlank(message = "Duration is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String duration;

    @Size(max = 1000)
    @Column(length = 1000)
    private String instructions;

    @Column(nullable = false, updatable = false)
    private LocalDateTime prescribedAt;

    @PrePersist
    protected void onCreate() {
        if (prescribedAt == null) {
            prescribedAt = LocalDateTime.now();
        }
    }
}
