package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.model.UserDto;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class BookingDto {
    @Nullable
    Long id;
    @NonNull LocalDateTime start;
    @NonNull LocalDateTime end;
    @NonFinal
    @Setter
    @Nullable
    ItemDto item;
    @Nullable
    UserDto booker;
    @Builder.Default
    Status status = Status.WAITING;
}
