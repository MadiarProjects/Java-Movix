package com.example.movix.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Setter
@Getter
public class User {
    private  int id;
    @NotBlank private final String login;
    private  String name;
    @NotBlank @Email private String email;
    @NotNull @PastOrPresent private final LocalDate birthday;
    private  List<User> friends;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
