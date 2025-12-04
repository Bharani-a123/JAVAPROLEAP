package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.Expense;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController extends BaseController {
    
    private final ExpenseService expenseService;
    
    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody Expense expense) {
        try {
            User user = getCurrentUser();
            expense.setUser(user);
            Expense savedExpense = expenseService.addExpense(expense);
            return ResponseEntity.ok(savedExpense);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses() {
        User user = getCurrentUser();
        List<Expense> expenses = expenseService.getUserExpenses(user);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseService.getUserExpensesByDateRange(user, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable Expense.Category category) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseService.getUserExpensesByCategory(user, category);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/total")
    public ResponseEntity<Map<String, BigDecimal>> getTotalExpenses(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = getCurrentUser();
        BigDecimal total = expenseService.getTotalExpenses(user, startDate, endDate);
        return ResponseEntity.ok(Map.of("total", total));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        User user = getCurrentUser();
        return expenseService.findById(id)
                .filter(existingExpense -> existingExpense.getUser().getId().equals(user.getId()))
                .map(existingExpense -> {
                    expense.setId(id);
                    expense.setUser(user);
                    return ResponseEntity.ok(expenseService.updateExpense(expense));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        try {
            User user = getCurrentUser();
            return expenseService.findById(id)
                    .filter(expense -> expense.getUser().getId().equals(user.getId()))
                    .map(expense -> {
                        expenseService.deleteExpense(id);
                        return ResponseEntity.ok(Map.of("message", "Expense deleted successfully"));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}