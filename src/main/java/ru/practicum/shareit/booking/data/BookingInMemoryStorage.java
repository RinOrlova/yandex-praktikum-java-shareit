package ru.practicum.shareit.booking.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.ItemDto;

import java.util.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookingInMemoryStorage implements BookingStorage{

    private final Map<Long, BookingDto> bookingMap = new HashMap<>();
    private Long nextId = 0L;
    @Override
    public BookingDto add(BookingDto bookingDto) {
        log.info("Attempt to add booking {}", bookingDto);
        BookingDto bookingWithId = bookingDto.toBuilder()
                .id(getNextValidId())
                .build();
        bookingMap.put(bookingWithId.getId(), bookingWithId);
        log.info("Booking with id={} successfully added", bookingWithId.getId());
        return bookingWithId;
    }

    @Override
    public BookingDto update(BookingDto bookingDto) {
        Long bookingId = bookingDto.getId();
        if (bookingMap.containsKey(bookingId)) {
            log.info("Attempt to change booking with id={}", bookingId);
            bookingMap.put(bookingId, bookingDto);
            log.info("Item with id={} successfully updated", bookingId);
            return bookingDto;
        }
        log.warn("Booking with id={} is not present", bookingId);
        throw new BookingNotFoundException(bookingId);
    }

    @Override
    public Collection<BookingDto> getAll() {
        return new ArrayList<>(bookingMap.values());
    }

    @Override
    public Optional<BookingDto> getById(Long id) {
        return Optional.ofNullable(bookingMap.get(id));
    }

    private Long getNextValidId() {
        nextId++;
        return nextId;
    }
}
