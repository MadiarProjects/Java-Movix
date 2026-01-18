package com.example.movix.controller;

import com.example.movix.model.Film;
import com.example.movix.model.Mpa;
import com.example.movix.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaStorage mpaStorage;
    @GetMapping
    public List<Mpa>getMaps(){
        return mpaStorage.getAll();
    }
    @GetMapping("/{id}")
    public Mpa getById(@PathVariable final Long id){
        return mpaStorage.getById(id);
    }
}
