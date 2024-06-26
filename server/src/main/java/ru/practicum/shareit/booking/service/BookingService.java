package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;

import java.util.Collection;

public interface BookingService {
    BookingResponse addBooking(BookingRequest booking, Long userId);

    BookingResponse updateBooking(Long userId, Long bookingId, boolean isApproved);

    BookingResponse getBookingByIdAndUserId(Long bookingId, Long userId);

    Collection<BookingResponse> getBookingsByUser(Long userId, State state, int from, int size);

    Collection<BookingResponse> getBookingsByOwnerId(Long ownerId, State state, int from, int size);

}
