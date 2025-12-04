package com.vaccinetracker.vaccinetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinetracker.vaccinetracker.model.Vaccine;
import com.vaccinetracker.vaccinetracker.repository.VaccineRepository;

@RestController
@RequestMapping("/api/vaccines")
@CrossOrigin
public class VaccineController {

    @Autowired private VaccineRepository vaccineRepo;

    @PostMapping
    public Vaccine addVaccine(@RequestBody Vaccine vaccine) {
        return vaccineRepo.save(vaccine);
    }

    @GetMapping
    public List<Vaccine> getAllVaccines() {
        return vaccineRepo.findAll();
    }
}
