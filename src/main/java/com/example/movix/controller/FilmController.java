package com.example.movix.controller;

import com.example.movix.exceptions.ValidationException;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import com.example.movix.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final List<Film> films=new ArrayList<>();
    private static int nextId=0;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("дата релиза не должна быть раньше 1895,12,28");
        }
        film.setId(++nextId);
        films.add(film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return films;
    }
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.contains(film)){
            films.removeIf(u->u.getId()==film.getId());
            films.add(film);
            return film;
        }else {
            throw new ValidationException("не правильный айди");
        }

    }

}
