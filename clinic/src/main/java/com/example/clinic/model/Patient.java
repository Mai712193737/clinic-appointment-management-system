package com.example.clinic.model;

import com.example.clinic.model.enums.Gender;

import java.util.UUID;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    public Patient() {
    }

    public Patient(String firstName,String lastName, LocalDateTime birthDate, String phoneNumber, Gender gender, String email, String password) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.phoneNumber=phoneNumber;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
    }

}

