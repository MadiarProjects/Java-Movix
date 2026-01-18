package com.example.movix.controller;

import com.example.movix.model.Genre;
import com.example.movix.storage.GenreStorage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    //get genres
    private final GenreStorage genreStorage;
    @GetMapping
    public List<Genre> getGenres(){
        return genreStorage.getAll();
    }
    @GetMapping("/{id}")
    public Genre getById(@PathVariable Long id){
        return genreStorage.getById(id);
    }
}
