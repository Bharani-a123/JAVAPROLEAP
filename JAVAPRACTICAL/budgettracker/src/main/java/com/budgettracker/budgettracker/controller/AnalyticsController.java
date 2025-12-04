package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController extends BaseController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        User user = getCurrentUser();
        Map<String, Object> dashboardData = analyticsService.getDashboardData(user);
        return ResponseEntity.ok(dashboardData);
    }
    
    @GetMapping("/monthly-trend")
    public ResponseEntity<Map<String, BigDecimal>> getMonthlyExpenseTrend(
            @RequestParam(defaultValue = "6") int months) {
        User user = getCurrentUser();
        Map<String, BigDecimal> trend = analyticsService.getMonthlyExpenseTrend(user, months);
        return ResponseEntity.ok(trend);
    }
    
    @GetMapping("/category-expenses")
    public ResponseEntity<Map<Expense.Category, BigDecimal>> getCategoryWiseExpenses(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = getCurrentUser();
        Map<Expense.Category, BigDecimal> categoryExpenses = analyticsService.getCategoryWiseExpenses(user, startDate, endDate);
        return ResponseEntity.ok(categoryExpenses);
    }
}