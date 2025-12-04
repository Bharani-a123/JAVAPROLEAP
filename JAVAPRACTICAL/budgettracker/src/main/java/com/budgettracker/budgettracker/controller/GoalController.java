package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.Goal;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController extends BaseController {
    
    private final GoalService goalService;
    
    @PostMapping
    public ResponseEntity<?> createGoal(@RequestBody Goal goal) {
        try {
            User user = getCurrentUser();
            goal.setUser(user);
            Goal savedGoal = goalService.createGoal(goal);
            return ResponseEntity.ok(savedGoal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Goal>> getUserGoals() {
        User user = getCurrentUser();
        List<Goal> goals = goalService.getUserGoals(user);
        return ResponseEntity.ok(goals);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Goal>> getActiveGoals() {
        User user = getCurrentUser();
        List<Goal> activeGoals = goalService.getActiveGoals(user);
        return ResponseEntity.ok(activeGoals);
    }
    
    @GetMapping("/completed")
    public ResponseEntity<List<Goal>> getCompletedGoals() {
        User user = getCurrentUser();
        List<Goal> completedGoals = goalService.getCompletedGoals(user);
        return ResponseEntity.ok(completedGoals);
    }
    
    @PostMapping("/{id}/add")
    public ResponseEntity<?> addToGoal(@PathVariable Long id, @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal amount = request.get("amount");
            Goal updatedGoal = goalService.addToGoal(id, amount);
            return ResponseEntity.ok(updatedGoal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        User user = getCurrentUser();
        return goalService.findById(id)
                .filter(existingGoal -> existingGoal.getUser().getId().equals(user.getId()))
                .map(existingGoal -> {
                    goal.setId(id);
                    goal.setUser(user);
                    return ResponseEntity.ok(goalService.updateGoal(goal));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id) {
        try {
            User user = getCurrentUser();
            return goalService.findById(id)
                    .filter(goal -> goal.getUser().getId().equals(user.getId()))
                    .map(goal -> {
                        goalService.deleteGoal(id);
                        return ResponseEntity.ok(Map.of("message", "Goal deleted successfully"));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}