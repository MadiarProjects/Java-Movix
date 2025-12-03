package com.example.movix.storage;

import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final List<User> users = new ArrayList<>();
    private int nextId;
    @Override
    public List<User> getUsers(){
        return users;
    }
    @Override
    public User addUser(User user){
        if (user.getName()==null||user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        user.setId(++nextId);
        users.add(user);
        return user;
    }
    @Override
    public User removeUser(User user){
        if (!(user ==null)){
            users.removeIf(u->u.getId()==user.getId());
        }else {
            throw new NotFoundedException("пользователя с таким айди нет"+user.getId());
        }
        return user;
    }
    @Override
    public User updateUser(User user){
        if(users.contains(user)){
            users.removeIf(u->u.getId()==user.getId());
        }
        users.add(user);
        return user;
    }
}
