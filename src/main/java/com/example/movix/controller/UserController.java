package com.example.movix.controller;

import lombok.extern.slf4j.Slf4j;
import com.example.movix.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        validateUSer(user);
        users.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        users.removeIf(user1 -> user1.getId() == user.getId());
        users.add(user);
        return user;
    }

    private void validateUSer(User user) {
        if (users.contains(user)){
            throw new RuntimeException("такой пользователь уже существует");
        }
    }
}
