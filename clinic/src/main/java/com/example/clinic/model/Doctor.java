package com.example.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String specialization;

    private String email;

    private String phoneNumber;

    public Doctor() {
    }

    public Doctor(String name, String specialization, String email, String phoneNumber) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}