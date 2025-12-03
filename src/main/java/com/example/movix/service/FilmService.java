package com.example.movix.service;

import com.example.movix.exceptions.AlreadyExictException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.User;
import com.example.movix.storage.FilmStorage;
import com.example.movix.storage.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
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
    public List<Film> getPopulars(int count){
        List<Film> films= filmStorage.getFilms();
        if (count>0){
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
    public String addLike(int filmId,int userId){
        User user = userStorage.getById(userId);
        Film film=filmStorage.getById(filmId);
        if (user.getLikedFilms().contains(film)){
            throw new AlreadyExictException("этот пользователь уже ставил лайк на этот фильм");
        }else{
            film.getLikes().add(user);
            user.getLikedFilms().add(film);
            return "у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков";
        }
    }
    public String deleteLike(int filmId,int userId){
        User user = userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        if (!user.getLikedFilms().contains(film)){
            throw new NotFoundedException("этот пользователь не ставил лайк на этот фильм ещё");
        }else {
            film.getLikes().remove(user);
            user.getLikedFilms().remove(film);
            return "у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков";
        }
    }

}
