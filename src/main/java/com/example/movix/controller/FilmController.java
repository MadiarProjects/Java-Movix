package com.example.movix.controller;

import com.example.movix.exceptions.ValidationException;
import com.example.movix.model.User;
import com.example.movix.service.FilmService;
import com.example.movix.service.UserService;
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

   private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getAll();
    }
    @GetMapping("/popular?count={count}")
    public List<Film> getPopularFilms(@PathVariable(required = false) final int count){
        return filmService.getPopulars(count);
    }
    @GetMapping("/{id}")
    public Film getById(@PathVariable final int id){
        return filmService.getById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }
    @PutMapping("/{filmId}/like/{userId}")
    public String addLikeTofilm(@PathVariable final int filmId,@PathVariable final int userId){
        return filmService.addLike(filmId,userId);
    }
    @DeleteMapping("/{id}")
    public void removeFilm(@PathVariable final int id){
        filmService.remove(id);
    }
    @DeleteMapping
    public String deleteLikeFromFIlm(@PathVariable final int filmId,@PathVariable final int userId){
        return filmService.deleteLike(filmId,userId);
    }
}
