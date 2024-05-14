package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
public class ItemDto {

    @Nullable
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String description;
    boolean available;
    @NonNull
    Long ownerId;
    @Nullable
    Long requestId;
}