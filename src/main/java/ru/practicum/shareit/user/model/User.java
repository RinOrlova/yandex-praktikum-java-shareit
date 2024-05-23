package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class User {

    @Nullable
    Long id;
    @NotBlank String name;
    @Email @NotNull String email;
}
