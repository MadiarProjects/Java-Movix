package com.example.movix.controller;

import com.example.movix.model.Mpa;
import com.example.movix.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
