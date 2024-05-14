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
        BookingEntity bookingEntity = bookingMapper.bookingDto2BookingEntity(bookingDto);
        BookingEntity savedEntity = bookingRepository.save(bookingEntity);
        return bookingMapper.bookingEntity2BookingDto(savedEntity);
    }

    @Override
    public BookingDto update(BookingDto bookingDto) {
        BookingEntity bookingEntity = bookingMapper.bookingDto2BookingEntity(bookingDto);
        BookingEntity savedEntity = bookingRepository.save(bookingEntity);
        return bookingMapper.bookingEntity2BookingDto(savedEntity);
    }

    @Override
    public Collection<BookingDto> getAll() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::bookingEntity2BookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookingDto> getById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::bookingEntity2BookingDto);
    }
}
