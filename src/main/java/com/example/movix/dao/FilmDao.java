package com.example.movix.dao;

import com.example.movix.model.Film;

import java.util.List;

public interface FilmDao {
    public Film addFilm(Film film);
    public void removeFilm(int id);
    public Film getById(int id);
    public List<Film> getFilms();
    public Film updateFilm(Film film);
}
