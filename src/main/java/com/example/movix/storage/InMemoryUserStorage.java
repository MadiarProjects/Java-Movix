package com.example.movix.storage;

import com.example.movix.exceptions.AlreadyExictException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.User;
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
    public User getById(Long id){
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
    public void deleteById(Long id){
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

    @Override
    public void removeFriend(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
//        if (!(friend.getFriends().contains(user) || user.getFriends().contains(friend))) {
//            throw new NotFoundedException("пользователи не были в друзьях у друг друга");
//        }
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        update(user);
        update(friend);
        log.info(user.getName() + " и " + friend.getName() + " больше не друзья");
    }


    @Override
    public void addFriend(Long userId, Long friendId) {
        User user=getById(userId);
        User friend=getById(friendId);
        if (friend.getFriends().contains(user) || user.getFriends().contains(friend)) {
            throw new AlreadyExictException("пользователи уже состоят в друзьях у друг друга");
        }
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        update(user);
        update(friend);
        log.info(
                user.getName() + " теперь друзья с " + friend.getName());
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        List<User> commanFriends = new ArrayList<>(user.getFriends());
        commanFriends.retainAll(friend.getFriends());
        if (commanFriends.isEmpty()) {
            throw new NotFoundedException("нет общих друзей");
        }
        return commanFriends;
    }

    @Override
    public List<User> getFriendsById(Long userId) {
        return getById(userId).getFriends();
    }
}
