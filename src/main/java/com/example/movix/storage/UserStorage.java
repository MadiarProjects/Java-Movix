package com.example.movix.storage;

import com.example.movix.model.User;

import java.util.List;

public interface UserStorage {
    public User add(User user);
    public void deleteById(int id);
    public User getById(int id);
    public List<User> getAll();
    public User update(User user);

    public void removeFriend(Long userId,Long friendId);
    public void addFriend(Long userId,Long friendId);
    public List<User> findCommonFriends(Long userId,Long friendId);
    public List<User> getFriendsByID(Long userId);

}
