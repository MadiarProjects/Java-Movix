package com.example.movix.storage;

import com.example.movix.exceptions.AlreadyExictException;
import com.example.movix.exceptions.InvalidParamException;
import com.example.movix.exceptions.NotFoundedException;
import com.example.movix.model.Film;
import com.example.movix.model.Genre;
import com.example.movix.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private Long nextId=0L  ;
    private final UserStorage userStorage;
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
        User user = userStorage.getById(userId);
        Film film=getById(filmId);
        if (film.getLikes().contains(user)){
            throw new AlreadyExictException("этот пользователь уже ставил лайк на этот фильм");
        }else{
            film.getLikes().add(user);
            update(film);
            log.info("у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков");
        }
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        User user = userStorage.getById(userId);
        Film film = getById(filmId);
        if (!film.getLikes().contains(user)){
            throw new NotFoundedException("этот пользователь не ставил лайк на этот фильм ещё");
        }else {
            film.getLikes().remove(user);
            update(film);
            log.info("у фильма "+film.getName()+" "+ film.getLikes().size()+" лайков");
        }
    }

    @Override
    public List<Film> getPopulars(Long count) {
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

}
