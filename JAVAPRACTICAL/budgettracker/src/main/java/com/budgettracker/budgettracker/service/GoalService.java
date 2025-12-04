package com.budgettracker.budgettracker.service;

import com.budgettracker.budgettracker.entity.Goal;
import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService {
    
    private final GoalRepository goalRepository;
    
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }
    
    public List<Goal> getUserGoals(User user) {
        return goalRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Goal> getActiveGoals(User user) {
        return goalRepository.findByUserAndStatus(user, Goal.GoalStatus.ACTIVE);
    }
    
    public Optional<Goal> findById(Long id) {
        return goalRepository.findById(id);
    }
    
    @Transactional
    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }
    
    @Transactional
    public Goal addToGoal(Long goalId, BigDecimal amount) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));
        
        if (goal.isCompleted() && goal.getStatus() == Goal.GoalStatus.ACTIVE) {
            goal.setStatus(Goal.GoalStatus.COMPLETED);
        }
        
        return goalRepository.save(goal);
    }
    
    @Transactional
    public void deleteGoal(Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Goal not found with id: " + id);
        }
    }
    
    public List<Goal> getCompletedGoals(User user) {
        return goalRepository.findByUserAndStatus(user, Goal.GoalStatus.COMPLETED);
    }
}