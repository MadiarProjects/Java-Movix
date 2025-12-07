package com.example.movix.controller;

import com.example.movix.service.FilmService;
import jakarta.validation.Valid;
import com.example.movix.model.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

   private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getAll();
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) final Integer count){
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

    @PutMapping("/{filmId}/like/{userId}")
    public void addLikeTofilm(@PathVariable final int filmId,@PathVariable final int userId){
        filmService.addLike(filmId,userId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody(required = false) Film film) {
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    public void removeFilm(@PathVariable final int id){
        filmService.remove(id);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLikeFromFIlm(@PathVariable final int filmId,@PathVariable final int userId){
        filmService.deleteLike(filmId,userId);
    }
}
