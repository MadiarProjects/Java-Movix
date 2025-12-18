package com.example.movix.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Film {
    private long id;
    @NotBlank(message = "имя не может быть пустым")private String name;
    @Length(min = 10, max = 200, message = "длина описание не может быть меньше 10 букв или больше 200")private String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    private final List<User> likes=new ArrayList<>();
    private final Set<Long> genreIds=new HashSet<>();
    private final Set<Long> mpaIds=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && Objects.equals(genreIds, film.genreIds) && Objects.equals(mpaIds, film.mpaIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreIds, mpaIds);
    }
}
