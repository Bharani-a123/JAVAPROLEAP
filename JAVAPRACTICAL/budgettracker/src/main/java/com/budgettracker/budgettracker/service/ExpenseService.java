package com.budgettracker.budgettracker.service;

import com.budgettracker.budgettracker.entity.Budget;
import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;
    
    @Transactional
    public Expense addExpense(Expense expense) {
        Expense savedExpense = expenseRepository.save(expense);
        budgetService.updateBudgetSpending(expense.getUser(), expense.getCategory(), expense.getAmount());
        return savedExpense;
    }
    
    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUserOrderByExpenseDateDesc(user);
    }
    
    public List<Expense> getUserExpensesByDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndExpenseDateBetween(user, startDate, endDate);
    }
    
    public List<Expense> getUserExpensesByCategory(User user, Expense.Category category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }
    
    public BigDecimal getTotalExpenses(User user, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.getTotalExpensesByUserAndDateRange(user, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalExpensesByCategory(User user, Expense.Category category, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.getTotalExpensesByUserCategoryAndDateRange(user, category, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }
    
    @Transactional
    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    
    @Transactional
    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Expense not found with id: " + id);
        }
    }
}