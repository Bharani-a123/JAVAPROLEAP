package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.Budget;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController extends BaseController {
    
    private final BudgetService budgetService;
    
    @PostMapping
    public ResponseEntity<?> createBudget(@RequestBody Budget budget) {
        try {
            User user = getCurrentUser();
            budget.setUser(user);
            Budget savedBudget = budgetService.createBudget(budget);
            return ResponseEntity.ok(savedBudget);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Budget>> getUserBudgets() {
        User user = getCurrentUser();
        List<Budget> budgets = budgetService.getUserBudgets(user);
        return ResponseEntity.ok(budgets);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Budget>> getActiveBudgets() {
        User user = getCurrentUser();
        List<Budget> activeBudgets = budgetService.getActiveBudgets(user);
        return ResponseEntity.ok(activeBudgets);
    }
    
    @GetMapping("/alerts")
    public ResponseEntity<List<Budget>> getBudgetAlerts() {
        User user = getCurrentUser();
        List<Budget> alerts = budgetService.getBudgetsNearLimit(user, 80.0);
        return ResponseEntity.ok(alerts);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        User user = getCurrentUser();
        return budgetService.findById(id)
                .filter(existingBudget -> existingBudget.getUser().getId().equals(user.getId()))
                .map(existingBudget -> {
                    budget.setId(id);
                    budget.setUser(user);
                    return ResponseEntity.ok(budgetService.updateBudget(budget));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long id) {
        try {
            User user = getCurrentUser();
            return budgetService.findById(id)
                    .filter(budget -> budget.getUser().getId().equals(user.getId()))
                    .map(budget -> {
                        budgetService.deleteBudget(id);
                        return ResponseEntity.ok(Map.of("message", "Budget deleted successfully"));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}