package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository repo;
    
    @GetMapping()
    public List<User> gettAll() {
        return repo.findAll();
    }
    
    @PostMapping()
    public User add(@RequestBody User user) {
        return repo.save(user);
    }
    
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u) {
        User user = repo.findById(id).orElseThrow();
        user.setName(u.getName());
        user.setEmail(u.getEmail());
        return repo.save(user);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Delete ID:" + id;
    }
}