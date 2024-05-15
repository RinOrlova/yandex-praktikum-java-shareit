package ru.practicum.shareit.booking.model;

import lombok.*;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.validation.ValidBookingDate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder(toBuilder = true)
@ValidBookingDate
public class Booking {

    @Nullable
    Long id;
    @NotNull @FutureOrPresent LocalDateTime start;
    @NotNull @FutureOrPresent LocalDateTime end;
    @NotNull Long itemId;
    @Nullable Item item;
    @Nullable User booker;
    @Builder.Default
    Status status=Status.WAITING;
}
