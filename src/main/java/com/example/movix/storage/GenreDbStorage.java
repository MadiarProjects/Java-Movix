package com.example.movix.storage;

import com.example.movix.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@RequiredArgsConstructor
@Component
public class GenreDbStorage implements GenreStorage{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Genre> getAll() {
        String sql= """
                select 
                    g.id as genre_id,
                    g.name as genre_name
                    from genres g ;
                """;

        return jdbcTemplate.query(sql,this::mapRow);
    }

    private Genre mapRow(ResultSet rs,int rowNum)throws SQLException {
        Long id=rs.getLong("genre_id");
        String name=rs.getString("genre_name");
        return new Genre(id,name);
    }
}
