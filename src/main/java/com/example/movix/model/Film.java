package com.example.movix.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "имя не может быть пустым")private String name;
    @Length(min = 10, max = 200, message = "длина описание не может быть меньше 10 букв или больше 200")private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    @Positive
    private List<User> likes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
