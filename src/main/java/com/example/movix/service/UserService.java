package com.example.movix.service;

import com.example.movix.model.User;
import com.example.movix.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Component
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public void remove(Long id) {
        userStorage.deleteById(id);
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.getById(userId);
        userStorage.getById(friendId);
        userStorage.addFriend(userId,friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        userStorage.getById(userId);
        userStorage.getById(friendId);
        userStorage.removeFriend(userId,friendId);
    }

    public List<User> getFriendsById(Long userId) {
        userStorage.getById(userId);
        return userStorage.getFriendsById(userId);
    }

    public List<User> findCommonFriends(Long userId, Long friendId) {
        userStorage.getById(userId);
        userStorage.getById(friendId);
       return userStorage.findCommonFriends(userId,friendId);
    }

}
