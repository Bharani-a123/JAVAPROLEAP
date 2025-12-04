package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    
    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new RuntimeException("Invalid user principal");
        }
        
        return (User) principal;
    }
}