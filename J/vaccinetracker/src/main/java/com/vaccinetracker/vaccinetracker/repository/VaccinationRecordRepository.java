package com.vaccinetracker.vaccinetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vaccinetracker.vaccinetracker.model.VaccinationRecord;

public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Integer> {
    List<VaccinationRecord> findByUser_UserId(int userId);
    List<VaccinationRecord> findByVaccine_VaccineId(int vaccineId);

    @Query("SELECT r FROM VaccinationRecord r WHERE r.nextDueDate BETWEEN :today AND :limit")
    List<VaccinationRecord> findUpcomingDoses(LocalDate today, LocalDate limit);

    @Query("SELECT r FROM VaccinationRecord r WHERE r.status = 'Pending' AND r.nextDueDate < :today")
    List<VaccinationRecord> findMissedDoses(LocalDate today);
}
