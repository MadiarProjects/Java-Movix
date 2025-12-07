package com.example.movix.service;

import com.example.movix.exceptions.AlreadyExictException;
import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.User;
import com.example.movix.storage.FilmStorage;
import com.example.movix.storage.UserStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film add(Film film){
        return filmStorage.addFilm(film);
    }

    public List<Film> getAll(){
        return filmStorage.getFilms();
    }

    public Film getById(int id){
        return filmStorage.getById(id);
    }

    public List<Film> getPopulars(Integer count){
        List<Film> films= filmStorage.getFilms();
        if (count!=null){
            return films.stream()
                    .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                    .limit(count)
                    .toList();
        }
        else {
            return  films.stream()
                    .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                    .limit(10)
                    .toList();
        }
    }

    public Film update(Film film){
        return filmStorage.updateFilm(film);
    }

    public void remove(int id){
        filmStorage.removeFilm(id);
    }

    public void addLike(int filmId,int userId){
        User user = userStorage.getById(userId);
        Film film=filmStorage.getById(filmId);
        if (film.getLikes().contains(user)){
            throw new AlreadyExictException("этот пользователь уже ставил лайк на этот фильм");
        }else{
            film.getLikes().add(user);
            filmStorage.updateFilm(film);
             log.info("у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков");
        }
    }

    public void deleteLike(int filmId,int userId){
        User user = userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        if (!film.getLikes().contains(user)){
            throw new NotFoundedException("этот пользователь не ставил лайк на этот фильм ещё");
        }else {
            film.getLikes().remove(user);
            filmStorage.updateFilm(film);
            log.info("у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков");
        }
    }

}
