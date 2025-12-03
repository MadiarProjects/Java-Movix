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
    public List<User> getAll(){
        return users;
    }
    @Override
    public User getById(int id){
        return (User) users.stream()
                .map(u-> u.getId()==id);
    }
    @Override
    public User add(User user){
        if (user.getName()==null||user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        user.setId(++nextId);
        users.add(user);
        return user;
    }
    @Override
    public void delete(int id){
        if (!(id==0)){
            users.remove(id);
        }else {
            throw new NotFoundedException("пользователя с таким айди нет"+id);
        }
        System.out.println("пользователь с айди " + id +"был удален");
    }
    @Override
    public User update(User user){
        if(users.contains(user)){
            users.removeIf(u->u.getId()==user.getId());
        }
        users.add(user);
        return user;
    }
}
