package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder(toBuilder = true)
public class Item {

    @Positive Long id;
    @NotNull String name;
    String description;
    @NotNull Boolean available;
    @Nullable User owner;
    @Nullable ItemRequest request;

}
