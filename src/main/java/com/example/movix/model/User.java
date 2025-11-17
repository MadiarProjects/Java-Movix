package com.example.movix.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class User {
    int id;
    @NotEmpty String login;
    String name;
    @NotBlank @Email String email;
    @NotNull @PastOrPresent LocalDate birthday;


    public static class UserBuilder {
        public User build() {
            if (this.name == null) {
                this.name = this.login;
            }
            return new User( id,email, login, name, birthday);
        }
    }
}
