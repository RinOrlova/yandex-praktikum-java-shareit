package ru.practicum.shareit.booking.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.BookingDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingStorageDatabase implements BookingStorage {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

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
