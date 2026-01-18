package com.example.movix.storage;

import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.Genre;
import com.example.movix.model.Mpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
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
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;
    @Override
    @Transactional
    public Film add(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("name", film.getName());
        map.put("description", film.getDescription());
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new InvalidParamException("дата выхода фильма не должна быть раньше 1895-12-28");
        } else {
            map.put("release_date", film.getReleaseDate());
        }
        map.put("duration", film.getDuration());
        if (!(film.getMpa().getId() > 5)) {
            map.put("mpa", film.getMpa().getId());
        } else {
            throw new NotFoundedException("рейтинга под этим айди не существует" + film.getMpa().getId());
        }
        Long number = insert.executeAndReturnKey(map).longValue();
        film.setId(number);
        if (!(film.getGenres() == null)) {
            film.getGenres().forEach(genre -> {
                String sqlForGenreIsExists = """
                        SELECT 
                        *
                        FROM genres g
                        WHERE g.id=?;
                        """;
                SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlForGenreIsExists, genre.getId());

                if (!srs.next()) {
                    throw new NotFoundedException("жанра с этим айди не существует" + genre.getId());
                }
                String sqlForGenres = """
                        INSERT INTO films_genres (film_id,genre_id)VALUES (?,?);
                        """;
                jdbcTemplate.update(sqlForGenres, film.getId(), genre.getId());
            });
        }
        return film;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        String sql = """
                delete from films where id=?;
                """;
        String sqlDeleteGenre = """
                delete from films_genres where film_id=?;
                """;
        jdbcTemplate.update(sqlDeleteGenre, id);
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Film getById(Long id) {
        String sql = """
                select
                f.id as film_id,
                f.name as film_name,
                f.description as film_description,
                f.release_date as film_release_date,
                f.duration as film_duration,
                f.mpa as film_mpa
                from films f
                where f.id=?;
                """;
        Film film = jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String sqlForGenres = """
                select 
                g.id as genre_id,
                g.name as genre_name
                from films_genres fg
                join genres g on fg.genre_id=g.id
                where fg.film_id = ?
                """;
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlForGenres, id);
        while (srs.next()) {
            Long genreId = srs.getLong("genre_id");
            if (genreId > 20) {
                throw new NotFoundedException("с таким айди жанра не существует" + genreId);
            } else {
                String genreName = srs.getString("genre_name");
                film.getGenres().add(new Genre(genreId, genreName));
            }
        }

        return film;
    }

    @Override
    public List<Film> getAll() {
        String sql = """
                select
                f.id as film_id,
                f.name as film_name,
                f.description as film_description,
                f.release_date as film_release_date,
                f.duration as film_duration,
                f.mpa as film_mpa
                from films f;
                """;
        return jdbcTemplate.query(sql, this::mapRow);

    }

    @Override
    @Transactional
    public Film update(Film film) {
        String sql = """
                UPDATE films SET name=?,description=?,duration=?,mpa=? WHERE id=?;
                """;
        String sqlForFilmExists = """
                SELECT 
                    *
                FROM films
                WHERE id=?
                """;
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlForFilmExists, film.getId());
        if (!srs.next()) {
            throw new NotFoundedException("нельзя обновить фильм с несуществующим айди" + film.getId());
        }
        int rowsAffected = jdbcTemplate
                .update(sql,
                        film.getName(),
                        film.getDescription(),
                        film.getDuration(),
                        film.getMpa().getId(),
                        film.getId()
                );

        if (rowsAffected == 0) {
            throw new InvalidParamException("не правильно заданы параметры");
        }
        return film;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sql = """
                INSERT INTO likes (film_id,user_id) VALUES (?,?);
                """;
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sql = """
                DELETE FROM likes WHERE film_id=? and user_id=?;
                """;
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getPopulars(Long count) {

        String sql = """
                                select
                                f.id as film_id,
                                f.name as film_name,
                                f.description as film_description,
                                f.release_date as film_release_date,
                                f.duration as film_duration,
                                f.mpa as film_mpa,
                                COUNT(l.user_id) AS likes_count
                                from films f
                LEFT JOIN likes l ON f.id = l.film_id
                GROUP BY f.id
                ORDER BY likes_count DESC
                LIMIT ?
               """;
        return jdbcTemplate.query(sql, this::mapRow, count);
    }

    private Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("film_id");
        String sqlForFilmExists = """
                SELECT 
                    *
                FROM films
                WHERE id=?
                """;
        SqlRowSet srsForFilmExists = jdbcTemplate.queryForRowSet(sqlForFilmExists, id);
        if (!srsForFilmExists.next()) {
            throw new NotFoundedException("нельзя обновить фильм с несуществующим айди" + id);
        }
        String name = rs.getString("film_name");
        String description = rs.getString("film_description");
        LocalDate releaseDate = rs.getDate("film_release_date").toLocalDate();
        int duration = rs.getInt("film_duration");
        Long mpaId = rs.getLong("film_mpa");
        Mpa mpa = null;
        if (mpaId <= 5) {
            String sqlForMpa = """
                    select
                    m.id as mpa_id,
                    m.name as mpa_name
                    from mpa m
                    where m.id=?;
                    """;
            SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlForMpa, mpaId);
            if (!srs.next()) {
                throw new NotFoundedException("");
            }
            String mpaName = srs.getString("mpa_name");
            mpaId = srs.getLong("mpa_id");
            mpa = new Mpa(mpaId, mpaName);
        } else {
            throw new NotFoundedException("рейтинга с таким айди не существует");
        }
        return new Film(id, name, description, releaseDate, duration, new HashSet<>(), mpa);
    }
}