package com.example.movix.storage;

import com.example.movix.model.Film;
import com.example.movix.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Film add(Film film) {
        SimpleJdbcInsert insert=new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map=new HashMap<>();
        map.put("name",film.getName());
        map.put("description",film.getDescription());
        map.put("release_date",film.getReleaseDate());
        map.put("duration",film.getDuration());
        map.put("mpa",film.getMpa());
        long number=insert.executeAndReturnKey(map).longValue();
        film.setId(number);
        return film;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        String sql= """
                delete from films where id=?;
                """;
        String sqlDeleteGenre= """
                delete from films_genres where film_id=?;
                """;
        jdbcTemplate.update(sqlDeleteGenre,id);
        int rowsAffected= jdbcTemplate.update(sql,id);
        if (rowsAffected==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Film getById(Long id) {
        String sql= """
                select
                f.id as film_id,
                f.name as film_name,
                f.description as film_description,
                f.release_date as film_release_date,
                f.duration as film_duration,
                f.mpa as film_mpa
                from films f
                where id=?;
                """;
        return  jdbcTemplate.query(sql,this::mapRow,id)
                .stream()
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Film> getAll() {
        String sql= """
                select
                f.id as film_id,
                f.name as film_name,
                f.description as film_description,
                f.release_date as film_release_date,
                f.duration as film_duration,
                f.mpa as film_mpa
                from films f;
                """;
        return  jdbcTemplate.query(sql,this::mapRow);

    }

    @Override
    public Film update(Film film) {
        return null;
    }
    private Film mapRow (ResultSet rs, int rowNum)throws SQLException {
        Long id=rs.getLong("film_id");
        String name=rs.getString("film_name");
        String description= rs.getString("film_description");
        LocalDate releaseDate =rs.getDate("film_release_date").toLocalDate();
        int duration =rs.getInt("film_duration");
        Long mpaId=rs.getLong("film_mpa");
        String sqlForMpa= """
                select 
                m.id as mpa_id,
                m.name as mpa_name
                from mpa m
                where m.id=?;
                """;
        SqlRowSet srs= jdbcTemplate.queryForRowSet(sqlForMpa,mpaId);
        String mpaName=srs.getString("mpa_name");
        mpaId = srs.getLong("mpa_id");
        Mpa mpa=new Mpa(mpaId,mpaName);
        return new Film(id,name,description,releaseDate,duration,mpa) ;
    }
}
