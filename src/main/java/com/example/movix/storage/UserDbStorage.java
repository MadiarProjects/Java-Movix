package com.example.movix.storage;

import com.example.movix.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage{
    private final JdbcTemplate jdbcTemplate;
    @Override
    @Transactional
    public User add(User user) {
        SimpleJdbcInsert insert=new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map=new HashMap<>();
//        map.put("столбец","значение");
        map.put("login",user.getLogin());
        map.put("name",user.getName());
        map.put("email",user.getEmail());
        map.put("birthday",user.getBirthday());

        long number=insert.executeAndReturnKey(map).longValue();
        user.setId(number);

        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        String sql= """
                delete from users where id=?;
                """;
        String sqlDeleteFriends= """
                delete from users_friends where user_id=?;
                """;
        jdbcTemplate.update(sqlDeleteFriends,id);
        int rowsAffected= jdbcTemplate.update(sql,id);
        if (rowsAffected==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public User getById(Long id) {
        String sql= """
                select
                u.id as user_id,
                u.name as user_name,
                u.email as user_email,
                u.login as user_login,
                u.birthday as user_birthday
                from users u
                where id=?;
                """;
        return  jdbcTemplate.query(sql,this::mapRow,id)
                .stream()
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAll() {
        String sql= """
                select
                u.id as user_id,
                u.name as user_name,
                u.email as user_email,
                u.login as user_login,
                u.birthday as user_birthday
                from users u ;
                """;

        return jdbcTemplate.query(sql,this::mapRow);
    }

    @Override
    @Transactional
    public User update(User user) {
        String sql= """
                update users set name=?,birthday = ? where id=?
                """;
        int rowsAffected= jdbcTemplate
                .update(sql,
                user.getName(),
                user.getBirthday(),
                user.getId()
                );
        if (rowsAffected==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sql = """
                delete from users_friends where user_id=? and friend_id=?;
        """;
        jdbcTemplate.update(sql,userId,friendId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sql= """
                insert into users_friends (user_id, friend_id) VALUES (?,?);
                """;
        jdbcTemplate.update(sql,userId,friendId);
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long friendId) {
        String sql= """
                select 
                u.id as user_id,
                u.name as user_name,
                u.email as user_email,
                u.login as user_login,
                u.birthday as user_birthday
                from users u 
                join users_friends uf1 on u.id = uf1.friend_id
                join users_friends uf2 on u.id=uf2.friend_id
                where uf1.user_id=? 
                and uf2.user_id=?;
                """;
        return jdbcTemplate.query(sql,this::mapRow,userId,friendId);
    }

    @Override
    public List<User> getFriendsById(Long userId) {
        String sql= """
                select 
                u.id as user_id,
                u.name as user_name,
                u.email as user_email,
                u.login as user_login,
                u.birthday as user_birthday
                from users u 
                join users_friends uf on u.id = uf.friend_id
                where uf.user_id=?;
                """;

        return jdbcTemplate.query(sql,this::mapRow,userId);
    }

    private User mapRow (ResultSet rs,int rowNum)throws SQLException {
        long id=rs.getLong("user_id");
        String login=rs.getString("user_login");
        String name=rs.getString("user_name");
        String email= rs.getString("user_email");
        LocalDate birthday= rs.getDate("user_birthday").toLocalDate();
        return new User(id,login,name,email,birthday);
    }
}
