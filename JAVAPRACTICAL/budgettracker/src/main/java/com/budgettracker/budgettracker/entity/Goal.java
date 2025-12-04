package com.budgettracker.budgettracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal targetAmount;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;
    
    @Column(name = "target_date")
    private LocalDate targetDate;
    
    @Enumerated(EnumType.STRING)
    private GoalStatus status = GoalStatus.ACTIVE;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user;
    
    public enum GoalStatus {
        ACTIVE, COMPLETED, PAUSED
    }
    
    public BigDecimal getRemainingAmount() {
        return targetAmount.subtract(currentAmount);
    }
    
    public double getProgressPercentage() {
        if (targetAmount.compareTo(BigDecimal.ZERO) == 0) return 0;
        return currentAmount.divide(targetAmount, 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();
    }
    
    public boolean isCompleted() {
        return currentAmount.compareTo(targetAmount) >= 0;
    }
}