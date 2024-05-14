package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

public interface BookingService {
    Booking addBooking(Booking booking, Long userId);

    Booking updateBooking(Long userId, Long bookingId, boolean isApproved);

    Booking getBookingById(Long bookingId);

    Collection<Booking> getBookingsByUser(Long userId, State state);

    Collection<Booking> getBookingsByOwnerId(Long ownerId, State state);

}
