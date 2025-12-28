package com.example.movix.storage;

import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.Genre;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private Long nextId;

    @Override
    public Film add(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new InvalidParamException("дата релиза не должна быть раньше 1895,12,28");
        }
        film.setId(++nextId);
        films.add(film);
        return film;
    }

    @Override
    public void deleteById(Long id) {
        Film film;
        if (!(id<=0)) {
            film = films.remove(Math.toIntExact(id));
        } else {
            throw new NotFoundedException("фильма под таким айди не существует"+id);
        }
        System.out.println("фильм успешно был удален :"+film.getName());

    }

    @Override
    public List<Film> getAll() {
        return films;
    }

    @Override
    public Film getById(Long id){
        return films.stream()
                .filter(f->f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundedException(""));
    }

    @Override
    public Film update(Film film) {
        if (films.contains(film)) {
            films.removeIf(f -> f.getId().equals(film.getId()));
            films.add(film);
            return film;
        } else {
            throw new NotFoundedException("с таким айди не существует ");
        }
    }

    @Override
    public void addLike(Long filmId, Long userId) {

    }

    @Override
    public void deleteLike(Long filmId, Long userId) {

    }

    @Override
    public List<Film> getPopulars(Long count) {
        return List.of();
    }

    @Override
    public void addGenre(Film film) {

    }

    @Override
    public List<Genre> getGenres() {
        return List.of();
    }
}
