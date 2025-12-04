package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.User;
import com.budgettracker.budgettracker.service.AnalyticsService;
import com.budgettracker.budgettracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebController {
    
    private final UserService userService;
    private final AnalyticsService analyticsService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("dashboardData", analyticsService.getDashboardData(user));
        model.addAttribute("user", user);
        return "dashboard";
    }
    
    @GetMapping("/expenses")
    public String expenses() {
        return "expenses";
    }
    
    @GetMapping("/budgets")
    public String budgets() {
        return "budgets";
    }
    
    @GetMapping("/goals")
    public String goals() {
        return "goals";
    }
    
    @GetMapping("/analytics")
    public String analytics() {
        return "analytics";
    }
}