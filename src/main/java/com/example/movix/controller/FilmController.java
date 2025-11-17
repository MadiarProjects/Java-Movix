package com.example.movix.controller;

import lombok.extern.slf4j.Slf4j;
import com.example.movix.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final List<Film> films=new ArrayList<>();
    @PostMapping
    public Film FilmController(@RequestBody Film film) {
        validateFilm(film);
        films.add(film);
        return film;
    }

    @GetMapping
    public List<Film> FilmController() {
        return films;
    }
    @PutMapping
    public Film FilmPutController(@RequestBody Film film) {
        films.removeIf(film1 -> film1.getName().equals(film.getName()));
        films.add(film);
        return film;
    }
    private void validateFilm (Film film){
        if (films.contains(film)) {
            throw new RuntimeException("такой фильм уже существует");
        }
    }
}
