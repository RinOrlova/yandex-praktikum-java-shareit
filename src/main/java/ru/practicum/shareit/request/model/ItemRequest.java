package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class ItemRequest {

    @Nullable
    Long id;
    @NonNull
    String description;
    @NonNull
    User requestor;
    @NonNull
    LocalDateTime created;

}
