package com.vaccinetracker.vaccinetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaccinetracker.vaccinetracker.model.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {}
