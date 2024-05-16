package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.validation.ValidBookingDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@ValidBookingDate
public class BookingRequest {

    @Nullable
    Long id;
    @NotNull @FutureOrPresent LocalDateTime start;
    @NotNull @FutureOrPresent LocalDateTime end;
    @NotNull Long itemId;

}
