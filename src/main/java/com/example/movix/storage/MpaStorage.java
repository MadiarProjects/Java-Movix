package com.example.movix.storage;

import com.example.movix.model.Mpa;

import java.util.List;

public interface MpaStorage {
    public List<Mpa> getAll();
    public Mpa getById(Long id);
}
