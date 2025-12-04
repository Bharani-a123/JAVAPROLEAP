package com.budgettracker.budgettracker.service;

import com.budgettracker.budgettracker.entity.Budget;
import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {
    
    private final BudgetRepository budgetRepository;
    
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    
    public List<Budget> getUserBudgets(User user) {
        return budgetRepository.findByUser(user);
    }
    
    public List<Budget> getActiveBudgets(User user) {
        return budgetRepository.findActiveBudgetsByUser(user, LocalDate.now());
    }
    
    public Optional<Budget> findById(Long id) {
        return budgetRepository.findById(id);
    }
    
    @Transactional
    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    
    @Transactional
    public void deleteBudget(Long id) {
        if (budgetRepository.existsById(id)) {
            budgetRepository.deleteById(id);
        } else {
            throw new RuntimeException("Budget not found with id: " + id);
        }
    }
    
    @Transactional
    public void updateBudgetSpending(User user, Expense.Category category, BigDecimal amount) {
        LocalDate currentDate = LocalDate.now();
        Optional<Budget> budgetOpt = budgetRepository.findByUserAndCategoryAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                user, category, currentDate, currentDate);
        
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            budget.setSpentAmount(budget.getSpentAmount().add(amount));
            budgetRepository.save(budget);
        }
    }
    
    public List<Budget> getBudgetsNearLimit(User user, double threshold) {
        return getActiveBudgets(user).stream()
                .filter(budget -> budget.getSpentPercentage() >= threshold)
                .toList();
    }
}