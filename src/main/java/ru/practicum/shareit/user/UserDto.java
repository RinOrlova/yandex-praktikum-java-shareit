package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
public class UserDto {
    @Nullable
    Long id;
    @NotBlank String name;
    @Email String email;
}
