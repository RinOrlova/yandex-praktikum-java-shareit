package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Value
@Builder(toBuilder = true)
public class ItemDto {

    @NotNull Long id;
    @NotNull String name;
    String description;
    @NotNull Boolean available;
    @Nullable User owner;
    @Nullable ItemRequest request;

}
