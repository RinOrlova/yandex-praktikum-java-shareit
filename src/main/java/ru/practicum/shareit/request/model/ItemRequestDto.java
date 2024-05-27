package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.user.model.UserDto;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class ItemRequestDto {

    @Nullable
    Long id;
    @NonNull
    String description;
    @NonNull
    UserDto requestor;
    @Builder.Default
    LocalDateTime created =  LocalDateTime.now();

}
