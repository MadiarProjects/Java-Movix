package com.example.movix.storage;

import com.example.movix.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film addFilm(Film film);
    public Film removeFilm(Film film);
    public List<Film> getFilms();
    public Film updateFilm(Film film);
}
