package com.vaccinetracker.vaccinetracker.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinetracker.vaccinetracker.model.VaccinationRecord;
import com.vaccinetracker.vaccinetracker.repository.VaccinationRecordRepository;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class ReportController {

    @Autowired private VaccinationRecordRepository recordRepo;

    @GetMapping("/upcoming-doses")
    public List<VaccinationRecord> upcomingDoses() {
        LocalDate today = LocalDate.now();
        return recordRepo.findAll().stream()
                .filter(r -> r.getNextDueDate() != null && r.getNextDueDate().isAfter(today))
                .collect(Collectors.toList());
    }

    @GetMapping("/missed-doses")
    public List<VaccinationRecord> missedDoses() {
        LocalDate today = LocalDate.now();
        return recordRepo.findAll().stream()
                .filter(r -> r.getNextDueDate() != null && r.getNextDueDate().isBefore(today))
                .collect(Collectors.toList());
    }
}
