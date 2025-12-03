package com.example.movix.storage;

import com.example.movix.model.User;

import java.util.List;

public interface UserStorage {
    public User add(User user);
    public void delete(int id);
    public User getById(int id);
    public List<User> getAll();
    public User update(User user);
}
