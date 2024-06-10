package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class BookingResponse {

    @NonNull
    Long id;
    @NonNull @FutureOrPresent LocalDateTime start;
    @NonNull @FutureOrPresent LocalDateTime end;
    @NonNull
    Item item;
    @NonNull
    User booker;
    @NonNull
    Status status;

}
