package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.Status;

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
    @NonNull Long itemId;
    @NonNull Long bookerId;
    @NotNull Status status;
}
