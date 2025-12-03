package com.example.movix.controller;

import com.example.movix.storage.InMemoryUserStorage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.example.movix.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;

    @GetMapping
    public List<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    @DeleteMapping
    public User removeUser(User user){
        return inMemoryUserStorage.removeUser(user);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateUser(user);
    }

}
