package com.budgettracker.budgettracker.repository;

import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserOrderByExpenseDateDesc(User user);
    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategory(User user, Expense.Category category);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpensesByUserAndDateRange(@Param("user") User user, 
                                                  @Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.category = :category AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpensesByUserCategoryAndDateRange(@Param("user") User user, 
                                                          @Param("category") Expense.Category category,
                                                          @Param("startDate") LocalDate startDate, 
                                                          @Param("endDate") LocalDate endDate);
}