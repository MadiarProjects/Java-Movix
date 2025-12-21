package com.example.movix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@AllArgsConstructor
public class User {
    private Long id;
    @NotBlank
    private final String login;
    private  String name;
    @NotBlank @Email
    private String email;
    @NotNull @PastOrPresent
    private final LocalDate birthday;
    @JsonIgnore
    private final List<User> friends=new ArrayList<>();

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
