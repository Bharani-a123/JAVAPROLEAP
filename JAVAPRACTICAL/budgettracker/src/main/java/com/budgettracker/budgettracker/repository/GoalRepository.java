package com.budgettracker.budgettracker.repository;

import com.budgettracker.budgettracker.entity.Goal;
import com.budgettracker.budgettracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserOrderByCreatedAtDesc(User user);
    List<Goal> findByUserAndStatus(User user, Goal.GoalStatus status);
}