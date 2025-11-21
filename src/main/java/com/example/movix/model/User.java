package com.example.movix.model;

import ch.qos.logback.core.joran.conditional.IfAction;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {
    private  int id;
    @NotBlank String login;
    private  String name;
    @NotBlank @Email private String email;
    @NotNull @PastOrPresent private LocalDate birthday;

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
