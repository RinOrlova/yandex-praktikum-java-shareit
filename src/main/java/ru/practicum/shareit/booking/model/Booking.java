package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.Status;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Booking {

    @Nullable
    Long id;
    @NotNull @FutureOrPresent LocalDateTime start;
    @NotNull @FutureOrPresent LocalDateTime end;
    @NotNull Long itemId;
    @NotNull Status status;
}
