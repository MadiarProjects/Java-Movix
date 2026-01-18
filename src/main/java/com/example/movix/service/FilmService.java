package com.example.movix.service;

import com.example.movix.exceptions.AlreadyExictException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.Genre;
import com.example.movix.model.User;
import com.example.movix.storage.FilmStorage;
import com.example.movix.storage.UserStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@AllArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film add(Film film){
        return filmStorage.add(film);
    }

    public List<Film> getAll(){
        return filmStorage.getAll();
    }

    public Film getById(Long id){
        return filmStorage.getById(id);
    }

    public List<Film> getPopulars(Long count){
        return filmStorage.getPopulars(count);
    }

    public Film update(Film film){
        return filmStorage.update(film);
    }

    public void remove(Long id){
        filmStorage.deleteById(id);
    }

    public void addLike(Long filmId, Long userId){
        filmStorage.addLike(filmId,userId);
    }

    public void deleteLike(Long filmId, Long userId){
        filmStorage.deleteLike(filmId,userId);
    }

}
