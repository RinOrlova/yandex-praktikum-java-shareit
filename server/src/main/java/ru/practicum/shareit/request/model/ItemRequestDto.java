package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.model.UserDto;

import java.time.LocalDateTime;
import java.util.Collection;

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
    LocalDateTime created = LocalDateTime.now();
    @Nullable
    Collection<ItemDto> itemDtos;

}
