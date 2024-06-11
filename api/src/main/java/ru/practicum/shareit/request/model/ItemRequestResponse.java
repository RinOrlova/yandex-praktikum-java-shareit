package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Collection;

@Value
@Builder
@Jacksonized
public class ItemRequestResponse {
    @NonNull
    Long id;
    @NonNull
    String description;
    @NonNull
    LocalDateTime created;
    @Nullable
    Collection<Item> items;
}
