package com.vaccinetracker.vaccinetracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vaccines")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vaccineId;

    @Column(nullable = false)
    private String name;

    private String manufacturer;
    private int recommendedDoses;
    private int intervalDays;
}
