package com.example.movix.controller;

import com.example.movix.service.UserService;
import jakarta.validation.Valid;
import com.example.movix.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        return userService.getById(id);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriendsById(@PathVariable int userId){
        return userService.getFriendsById(userId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    public List<User> findCommonFriends(@PathVariable int userId,@PathVariable int friendId){
        return userService.findCommonFriends(userId,friendId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId,@PathVariable int friendId){
         userService.addFriend(userId,friendId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable int id){
        userService.remove(id);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeFriend(@PathVariable int userId,@PathVariable int friendId){
        userService.removeFriend(userId,friendId);
    }

}
