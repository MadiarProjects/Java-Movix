package com.example.movix.storage;

import com.example.movix.model.Film;
import com.example.movix.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserStorage {
    public User addUser(User user);
    public User removeUser(User user);
    public List<User> getUsers();
    public User updateUser(User user);
}
