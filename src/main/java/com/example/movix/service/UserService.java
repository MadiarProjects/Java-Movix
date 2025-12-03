package com.example.movix.service;

import com.example.movix.model.User;
import com.example.movix.storage.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Component
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User getById(int id) {
        return userStorage.getById(id);
    }
    public List<User> getAll(){
        return userStorage.getAll();
    }
    public User update(User user) {
        return userStorage.update(user);
    }
    public void remove(int id){
         userStorage.delete(id);
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userStorage.update(user);
        userStorage.update(friend);
        System.out.println(user.getName() + " теперь друзья с " + friend.getName());
    }

    public void removeFriend(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userStorage.update(user);
        userStorage.update(friend);
        System.out.println(user.getName()+" и "+friend.getName()+" больше не друзья");
    }

    public List<User> getFriendsById(int userId) {
        User user = userStorage.getById(userId);
        return user.getFriends();
    }

    public List<User> findCommonFriends(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        List<User> commanFriends=new ArrayList<>(user.getFriends());
        commanFriends.retainAll(friend.getFriends());
        return commanFriends;
    }

}
