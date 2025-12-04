package com.budgettracker.budgettracker.repository;

import com.budgettracker.budgettracker.entity.Budget;
import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    Optional<Budget> findByUserAndCategoryAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            User user, Expense.Category category, LocalDate date1, LocalDate date2);
    
    @Query("SELECT b FROM Budget b WHERE b.user = :user AND :currentDate BETWEEN b.startDate AND b.endDate")
    List<Budget> findActiveBudgetsByUser(@Param("user") User user, @Param("currentDate") LocalDate currentDate);
}