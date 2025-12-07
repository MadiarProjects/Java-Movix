package com.example.movix.storage;

import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryUserStorage implements UserStorage{
    private final List<User> users = new ArrayList<>();
    private int nextId;
    @Override
    public List<User> getAll(){
        return users;
    }
    @Override
    public User getById(int id){
        User user=null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId()==id){
                user=users.get(i);
            }
        }
        if (user==null){
            throw new NotFoundedException("пользователя с таким айди не существует");
        }
        return user;
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
        log.info("пользователь с айди " + id +"был удален");
    }
    @Override
    public User update(User user){
        if(users.contains(user)){
            users.removeIf(u->u.getId()==user.getId());
            users.add(user);
        }else {
            throw new NotFoundedException("пользователя с таким айди не существует");
        }
        return user;
    }
}
