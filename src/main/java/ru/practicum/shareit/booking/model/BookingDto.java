package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class BookingDto {
    @Nullable
    Long id;
    @NonNull LocalDateTime start;
    @NonNull LocalDateTime end;
    @NonNull Item item;
    @NonNull User booker;
    @NotNull Status status;
}
