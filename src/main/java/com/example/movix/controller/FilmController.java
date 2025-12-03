package com.example.movix.controller;

import com.example.movix.exceptions.ValidationException;
import com.example.movix.storage.InMemoryFilmStorage;
import jakarta.validation.Valid;
import com.example.movix.model.Film;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/films")
public class FilmController {

   private final InMemoryFilmStorage inMemoryFilmStorage;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @DeleteMapping
    public Film removeFilm(Film film){
        return inMemoryFilmStorage.removeFilm(film);
    }
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }
}
