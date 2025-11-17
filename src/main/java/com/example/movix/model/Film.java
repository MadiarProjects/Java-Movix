package com.example.movix.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Value
@Builder
public class Film {

    @NotEmpty(message = "имя не может быть пустым")
    String name;
    @Length(min = 10, max = 200, message = "длина описание не может быть меньше 10 букв или больше 200")
    String description;
    LocalDate releaseDate;
    @Positive
    int duration;

    public static class FilmBuilder {
        public Film build() {
            LocalDate firstFilmDate = LocalDate.of(1895, 12, 28);
            if (this.releaseDate.isBefore(firstFilmDate)) {
                throw new IllegalArgumentException("Дата релиза не может быть раньше 28.12.1895");
            }
            return new Film( name, description, releaseDate, duration);
        }

    }
}
