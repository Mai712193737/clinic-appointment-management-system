package com.example.clinic.model;

import com.example.clinic.model.enums.Day;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "working_periods")
public class WorkingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private Day dayOfWeek;

    @Column(name = "starting_time", nullable = false)
    private LocalTime startingTime;

    @Column(name = "ending_time", nullable = false)
    private LocalTime endingTime;

    public WorkingPeriod(Doctor doctor, Day dayOfWeek, LocalTime startingTime, LocalTime endingTime) {
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }
}