package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.data.BookingStorage;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Qualifier("bookingStorageDatabase")
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking addBooking(Booking booking, Long userId) {
        checkUser(userId);
        Long itemOwnerId = itemService.getOwnerForItemByItemId(booking.getItemId());
        if (booking.getStart().isBefore(booking.getEnd())) {
            if(!itemOwnerId.equals(userId)) {
                BookingDto bookingDto = bookingMapper.booking2BookingDto(booking, userId);
                BookingDto bookingFromStorage = bookingStorage.add(bookingDto);
                return bookingMapper.bookingDto2Booking(bookingFromStorage);
            }
        }
        throw new ForbiddenException();
    }

    @Override
    public Booking updateBooking(Long userId, Long bookingId, boolean isApproved) {
        return null;
    }

    @Override
    public Booking getBookingById(Long bookingId, Long userId) {
        // todo validation for user. throw exception if user is not an owner or booker
        BookingDto bookingFromStorage = bookingStorage.getById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        return bookingMapper.bookingDto2Booking(bookingFromStorage);
    }

    @Override
    public Collection<Booking> getBookingsByUser(Long userId, State state) {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsByOwnerId(Long ownerId, State state) {
        return null;
    }

    private User checkUser(Long id) {
        return userService.getUserById(id);
    }

    private Item checkItem(Long id) {
        return itemService.getItemById(id);
    }
}
