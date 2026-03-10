package com.example.sehetak_bethakaa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_health_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHealthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String name;

    private LocalDate birthday;

    private Double height; // cm

    private Double weight; // kg

    private Double bmi;

    private String bloodType;

    private boolean doUserSmoke;

    @ManyToMany
    @JoinTable(
            name = "user_diseases",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private List<Disease> diseases;

    @ManyToMany
    @JoinTable(
            name = "user_history_diseases",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private List<Disease> historyDiseases;

    private String notes;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}