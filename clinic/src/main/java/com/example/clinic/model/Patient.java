package com.example.clinic.model;

import java.util.UUID;

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
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    public Patient() {
    }

    public Patient(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}

