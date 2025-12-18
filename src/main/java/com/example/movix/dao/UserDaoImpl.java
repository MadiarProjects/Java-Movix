package com.example.movix.dao;

import com.example.movix.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public User add(User user) {
        SimpleJdbcInsert insert=new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map=new HashMap<>();
//        map.put("столбец","значение");
        map.put("name",user.getName());
        map.put("")
        long number=insert.executeAndReturnKey(map).longValue();
        user.setId(number);
        return user;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public User update(User user) {
        return null;
    }
}
