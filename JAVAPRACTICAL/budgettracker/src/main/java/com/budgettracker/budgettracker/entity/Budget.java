package com.budgettracker.budgettracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Expense.Category category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetLimit;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
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
    
    public BigDecimal getRemainingAmount() {
        return budgetLimit.subtract(spentAmount);
    }
    
    public boolean isExceeded() {
        return spentAmount.compareTo(budgetLimit) > 0;
    }
    
    public double getSpentPercentage() {
        if (budgetLimit.compareTo(BigDecimal.ZERO) == 0) return 0;
        return spentAmount.divide(budgetLimit, 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();
    }
}