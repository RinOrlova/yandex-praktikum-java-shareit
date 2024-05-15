package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
@Builder(toBuilder = true)
public class ItemDto {

    @Nullable
    Long id;
    @Nullable String name;
    @Nullable String description;
    boolean available;
    @Nullable Long ownerId;
    @Nullable Long requestId;
}