package com.vaccinetracker.vaccinetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaccinetracker.vaccinetracker.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
