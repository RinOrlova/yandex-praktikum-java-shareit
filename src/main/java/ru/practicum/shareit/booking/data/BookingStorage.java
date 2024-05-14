package ru.practicum.shareit.booking.data;

import ru.practicum.shareit.booking.model.BookingDto;

import java.util.Collection;
import java.util.Optional;

public interface BookingStorage {

    BookingDto add(BookingDto bookingDto);

    BookingDto update(BookingDto bookingDto);

    Collection<BookingDto> getAll();

    Optional<BookingDto> getById(Long id);

}
