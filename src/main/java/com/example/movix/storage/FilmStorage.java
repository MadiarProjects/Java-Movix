package com.example.movix.storage;

import com.example.movix.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film addFilm(Film film);
    public void removeFilm(int id);
    public Film getById(int id);
    public List<Film> getFilms();
    public Film updateFilm(Film film);
}
