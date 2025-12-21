package com.example.movix.storage;

import com.example.movix.model.Genre;
import com.example.movix.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {

        String sql = """
                select 
                    m.id as mpa_id,
                    m.name as mpa_name
                    from mpa m ;
                """;

        return jdbcTemplate.query(sql, this::mapRow);
    }
    private Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("mpa_id");
        String name = rs.getString("mpa_name");
        return new Mpa(id, name);
    }
}
