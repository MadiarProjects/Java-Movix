package com.example.movix.storage;

import com.example.movix.model.Film;
import com.example.movix.model.Genre;

import java.util.List;

public interface FilmStorage {
    public Film add(Film film);
    public void deleteById(Long id);
    public Film getById(Long id);
    public List<Film> getAll();
    public Film update(Film film);

    public void addLike(Long filmId,Long userId);
    public void deleteLike(Long filmId,Long userId);
    public List<Film> getPopulars(Long count);
}
