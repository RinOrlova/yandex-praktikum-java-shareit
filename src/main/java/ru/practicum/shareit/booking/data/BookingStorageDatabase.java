package ru.practicum.shareit.booking.data;

import ru.practicum.shareit.booking.model.BookingDto;

import java.util.Collection;
import java.util.Optional;

public class BookingStorageDatabase implements BookingStorage {
    @Override
    public BookingDto add(BookingDto bookingDto) {
        return null;
    }

    @Override
    public BookingDto update(BookingDto bookingDto) {
        return null;
    }

    @Override
    public Collection<BookingDto> getAll() {
        return null;
    }

    @Override
    public Optional<BookingDto> getById(Long id) {
        return Optional.empty();
    }
}
