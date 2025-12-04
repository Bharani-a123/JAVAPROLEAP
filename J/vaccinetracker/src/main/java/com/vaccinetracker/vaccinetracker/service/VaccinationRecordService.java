package com.vaccinetracker.vaccinetracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinetracker.vaccinetracker.model.User;
import com.vaccinetracker.vaccinetracker.model.VaccinationRecord;
import com.vaccinetracker.vaccinetracker.model.Vaccine;
import com.vaccinetracker.vaccinetracker.repository.UserRepository;
import com.vaccinetracker.vaccinetracker.repository.VaccinationRecordRepository;
import com.vaccinetracker.vaccinetracker.repository.VaccineRepository;

@Service
public class VaccinationRecordService {
    @Autowired
    private VaccinationRecordRepository recordRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VaccineRepository vaccineRepo;

    public VaccinationRecord saveRecord(VaccinationRecord record) {
        User user = userRepo.findById(record.getUser().getUserId()).orElseThrow();
        Vaccine vaccine = vaccineRepo.findById(record.getVaccine().getVaccineId()).orElseThrow();

        // Dose validation
        if (record.getDoseNumber() > vaccine.getRecommendedDoses()) {
            throw new RuntimeException("Dose number exceeds recommended doses");
        }

        // Compute next due date
        if (record.getDoseNumber() < vaccine.getRecommendedDoses()) {
            record.setNextDueDate(record.getVaccinationDate().plusDays(vaccine.getIntervalDays()));
            record.setStatus("Pending");
        } else {
            record.setNextDueDate(null);
            record.setStatus("Completed");
        }

        return recordRepo.save(record);
    }

    public List<VaccinationRecord> getAllRecords() {
        return recordRepo.findAll();
    }

    public List<VaccinationRecord> getRecordsByUser(int userId) {
        return recordRepo.findByUser_UserId(userId);
    }

    public List<VaccinationRecord> getRecordsByVaccine(int vaccineId) {
        return recordRepo.findByVaccine_VaccineId(vaccineId);
    }

    public List<VaccinationRecord> getUpcomingDoses() {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(7);
        return recordRepo.findUpcomingDoses(today, limit);
    }

    public List<VaccinationRecord> getMissedDoses() {
        LocalDate today = LocalDate.now();
        return recordRepo.findMissedDoses(today);
    }
}
