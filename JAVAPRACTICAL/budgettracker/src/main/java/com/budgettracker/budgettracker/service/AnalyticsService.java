package com.budgettracker.budgettracker.service;

import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final GoalService goalService;
    
    public Map<String, Object> getDashboardData(User user) {
        Map<String, Object> data = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        
        // Current month expenses
        BigDecimal monthlyExpenses = expenseService.getTotalExpenses(user, startOfMonth, now);
        data.put("monthlyExpenses", monthlyExpenses);
        
        // Category-wise expenses
        Map<Expense.Category, BigDecimal> categoryExpenses = new HashMap<>();
        for (Expense.Category category : Expense.Category.values()) {
            BigDecimal amount = expenseService.getTotalExpensesByCategory(user, category, startOfMonth, now);
            categoryExpenses.put(category, amount);
        }
        data.put("categoryExpenses", categoryExpenses);
        
        // Active budgets
        data.put("activeBudgets", budgetService.getActiveBudgets(user));
        
        // Budget alerts (budgets over 80%)
        data.put("budgetAlerts", budgetService.getBudgetsNearLimit(user, 80.0));
        
        // Active goals
        data.put("activeGoals", goalService.getActiveGoals(user));
        
        // Recent expenses
        data.put("recentExpenses", expenseService.getUserExpensesByDateRange(user, now.minusDays(7), now));
        
        return data;
    }
    
    public Map<String, BigDecimal> getMonthlyExpenseTrend(User user, int months) {
        Map<String, BigDecimal> trend = new HashMap<>();
        LocalDate now = LocalDate.now();
        
        for (int i = 0; i < months; i++) {
            YearMonth yearMonth = YearMonth.from(now.minusMonths(i));
            LocalDate startOfMonth = yearMonth.atDay(1);
            LocalDate endOfMonth = yearMonth.atEndOfMonth();
            
            BigDecimal monthlyTotal = expenseService.getTotalExpenses(user, startOfMonth, endOfMonth);
            trend.put(yearMonth.toString(), monthlyTotal);
        }
        
        return trend;
    }
    
    public Map<Expense.Category, BigDecimal> getCategoryWiseExpenses(User user, LocalDate startDate, LocalDate endDate) {
        Map<Expense.Category, BigDecimal> categoryExpenses = new HashMap<>();
        
        for (Expense.Category category : Expense.Category.values()) {
            BigDecimal amount = expenseService.getTotalExpensesByCategory(user, category, startDate, endDate);
            categoryExpenses.put(category, amount);
        }
        
        return categoryExpenses;
    }
}