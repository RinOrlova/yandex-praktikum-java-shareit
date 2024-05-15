package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.model.UserDto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class BookingDto {
    @Nullable
    Long id;
    @NonNull @FutureOrPresent LocalDateTime start;
    @NonNull @FutureOrPresent LocalDateTime end;
    @Nullable ItemDto item;
    @Nullable UserDto booker;
    @Builder.Default
    @NotNull Status status = Status.WAITING;
}
