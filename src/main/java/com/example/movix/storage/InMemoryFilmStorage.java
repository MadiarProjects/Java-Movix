package com.example.movix.storage;

import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
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
    public void removeFilm(int id) {
        Film film;
        if (!(id<=0)) {
            film = films.remove(id);
        } else {
            throw new NotFoundedException("фильма под таким айди не существует"+id);
        }
        System.out.println("фильм успешно был удален :"+film.getName());

    }

    @Override
    public List<Film> getFilms() {
        return films;
    }

    @Override
    public Film getById(int id){
        return films.stream()
                .filter(f->f.getId()==id)
                .findFirst()
                .orElseThrow(() -> new NotFoundedException(""));
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.contains(film)) {
            films.removeIf(f -> f.getId() == film.getId());
            films.add(film);
            return film;
        } else {
            throw new NotFoundedException("с таким айди не существует ");
        }
    }
}
