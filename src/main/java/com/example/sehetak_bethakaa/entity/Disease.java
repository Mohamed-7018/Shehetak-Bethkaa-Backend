package com.example.sehetak_bethakaa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diseases")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String gender;

    private String type; // PERSONAL or FAMILY

    private String description;
}