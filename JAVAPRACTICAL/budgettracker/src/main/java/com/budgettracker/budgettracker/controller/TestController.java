package com.budgettracker.budgettracker.controller;

import com.budgettracker.budgettracker.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController extends BaseController {
    
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUserInfo() {
        try {
            User user = getCurrentUser();
            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "authenticated", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage(),
                "authenticated", false
            ));
        }
    }
}