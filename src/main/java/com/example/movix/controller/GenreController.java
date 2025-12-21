package com.example.movix.controller;

import com.example.movix.model.Genre;
import com.example.movix.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    //get genres
    private final GenreStorage genreStorage;
    @GetMapping
    public List<Genre> getGenres(){
        return genreStorage.getAll();
    }
}
