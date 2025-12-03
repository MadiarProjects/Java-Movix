package com.example.movix.storage;

import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.exceptions.ValidationException;
import com.example.movix.model.Film;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private int nextId;

    @Override
    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new InvalidParamException("дата релиза не должна быть раньше 1895,12,28");
        }
        film.setId(++nextId);
        films.add(film);
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        if (!(film == null)) {
            films.removeIf(f -> f.getId() == film.getId());
        } else {
            throw new NotFoundedException("такого фильма не сущетсвует");
        }
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return films;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.contains(film)) {
            films.removeIf(f -> f.getId() == film.getId());
            films.add(film);
            return film;
        } else {
            throw new InvalidParamException("не правильный айди");
        }
    }
}
