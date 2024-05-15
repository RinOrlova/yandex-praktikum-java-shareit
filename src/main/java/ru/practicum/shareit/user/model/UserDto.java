package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
@Builder(toBuilder = true)
public class UserDto {
    @Nullable Long id;
    @Nullable String name;
    @Nullable String email;
}