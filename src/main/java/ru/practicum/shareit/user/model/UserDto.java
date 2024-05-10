package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
public class UserDto {
    @Nullable
    Long id;
    @NotBlank
    String name;
    @Email
    @NonNull
    String email;
}