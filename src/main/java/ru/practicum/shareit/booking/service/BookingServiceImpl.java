package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.data.BookingStorage;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.ItemUnavailableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Qualifier("bookingStorageDatabase")
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingFilter bookingFilter;

    @Override
    public Booking addBooking(Booking booking, Long userId) {
        User user = checkUser(userId);
        if (!getItemOwner(booking).equals(userId)) {
            Long itemId = booking.getItemId();
            Item item = itemService.getItemById(itemId);
            if (Boolean.TRUE.equals(item.getAvailable())) {
                Booking recreatedBooking = booking.toBuilder()
                        .booker(user)
                        .item(item)
                        .build();

                BookingDto bookingDto = bookingMapper.booking2BookingDto(recreatedBooking);
                BookingDto bookingFromStorage = bookingStorage.add(bookingDto);
                return bookingMapper.bookingDto2Booking(bookingFromStorage);
            }
            throw new ItemUnavailableException(itemId);
        }
        throw new ForbiddenException();
    }

    @Override
    public Booking updateBooking(Long userId, Long bookingId, boolean isApproved) {
        checkUser(userId);
        BookingDto bookingDto = bookingStorage.getById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (isBookingOwner(bookingDto, userId)) {
            BookingDto updatedBookingDto = bookingDto.toBuilder()
                    .status(getCorrespondingStatus(isApproved))
                    .build();
            bookingStorage.update(updatedBookingDto);
        }
        throw new ForbiddenException();

    }

    private Status getCorrespondingStatus(boolean isApproved) {
        return isApproved
                ? Status.APPROVED
                : Status.REJECTED;
    }

    @Override
    public Booking getBookingById(Long bookingId, Long userId) {
        checkUser(userId);
        BookingDto bookingDto = bookingStorage.getById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        checkItem(bookingDto.getItem().getId());
        Booking booking = bookingMapper.bookingDto2Booking(bookingDto);
        if (bookingDto.getBooker().equals(userId) || getItemOwner(booking).equals(userId)) {
            return booking;
        }
        throw new ForbiddenException();
    }

    @Override
    public Collection<Booking> getBookingsByUser(Long userId, State state) {
        checkUser(userId);
        return bookingStorage.getAll().stream()
                .filter(bookingDto -> bookingDto.getBooker().equals(userId))
                .filter(bookingDto -> bookingFilter.isValidBooking(bookingDto, state))
                .map(bookingMapper::bookingDto2Booking)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Booking> getBookingsByOwnerId(Long ownerId, State state) {
        checkUser(ownerId);
        return bookingStorage.getAll().stream()
                .filter(bookingDto -> isBookingOwner(bookingDto, ownerId))
                .filter(bookingDto -> bookingFilter.isValidBooking(bookingDto, state))
                .map(bookingMapper::bookingDto2Booking)
                .collect(Collectors.toList());
    }

    private boolean isBookingOwner(BookingDto bookingDto, Long ownerId) {
        Long itemOwnerId = bookingDto.getItem().getOwnerId();
        return itemOwnerId.equals(ownerId);
    }

    private User checkUser(Long id) {
        return userService.getUserById(id);
    }

    private Item checkItem(Long id) {
        return itemService.getItemById(id);
    }

    private Long getItemOwner(Booking booking) {
        return itemService.getOwnerForItemByItemId(booking.getItemId());
    }
}
