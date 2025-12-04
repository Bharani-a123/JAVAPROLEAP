package com.vaccinetracker.vaccinetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinetracker.vaccinetracker.model.User;
import com.vaccinetracker.vaccinetracker.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired private UserRepository userRepo;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
