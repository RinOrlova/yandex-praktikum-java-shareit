package ru.practicum.shareit.booking.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.exception.BookingNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingStorageDatabase implements BookingStorage {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingDto add(BookingDto bookingDto) {
        BookingEntity bookingEntity = bookingMapper.bookingDto2BookingEntity(bookingDto);
        BookingEntity savedEntity = saveEntity(bookingEntity);
        bookingRepository.refresh(savedEntity);
        BookingEntity bookingEntityFromStore = bookingRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new BookingNotFoundException(savedEntity.getId()));
        return bookingMapper.bookingEntity2BookingDto(bookingEntityFromStore);
    }

    private BookingEntity saveEntity(BookingEntity bookingEntity) {
        return bookingRepository.saveAndFlush(bookingEntity);
    }

    @Override
    @Transactional
    public BookingDto update(BookingDto bookingDto) {
        BookingEntity bookingEntity = bookingMapper.bookingDto2BookingEntity(bookingDto);
        BookingEntity savedEntity = saveEntity(bookingEntity);
        bookingRepository.refresh(savedEntity);
        BookingEntity bookingEntityFromStore = bookingRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new BookingNotFoundException(savedEntity.getId()));
        return bookingMapper.bookingEntity2BookingDto(bookingEntityFromStore);
    }

    @Override
    public Collection<BookingDto> getAllByItemOwner(Long itemOwner, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return bookingRepository.findAllByItemOwner(itemOwner, pageable)
                .stream()
                .map(bookingMapper::bookingEntity2BookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<BookingDto> getAllForBookingOwner(Long userId, int from, int size) {
        int totalBookingByOwner = bookingRepository.findAllByBooker_Id(userId).size();
        int maxPageValue = (int) Math.ceil((double)totalBookingByOwner / size)                - 1;
        if (from > maxPageValue) {
            from = maxPageValue;
        }
        Pageable pageable = PageRequest.of(from, size);
        return bookingRepository.findAllForOwner(userId, pageable)
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
