package com.vaccinetracker.vaccinetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinetracker.vaccinetracker.model.VaccinationRecord;
import com.vaccinetracker.vaccinetracker.service.VaccinationRecordService;

@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "*")
public class VaccinationRecordController {
    @Autowired
    private VaccinationRecordService recordService;

    @PostMapping
    public VaccinationRecord addRecord(@RequestBody VaccinationRecord record) {
        return recordService.saveRecord(record);
    }

    @GetMapping
    public List<VaccinationRecord> getAll() {
        return recordService.getAllRecords();
    }

    @GetMapping("/user/{id}")
    public List<VaccinationRecord> getByUser(@PathVariable int id) {
        return recordService.getRecordsByUser(id);
    }

    @GetMapping("/vaccine/{id}")
    public List<VaccinationRecord> getByVaccine(@PathVariable int id) {
        return recordService.getRecordsByVaccine(id);
    }

    @GetMapping("/reports/upcoming-doses")
    public List<VaccinationRecord> getUpcoming() {
        return recordService.getUpcomingDoses();
    }

    @GetMapping("/reports/missed-doses")
    public List<VaccinationRecord> getMissed() {
        return recordService.getMissedDoses();
    }
}
