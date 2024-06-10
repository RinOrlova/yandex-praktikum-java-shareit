package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.BookingDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingFilterTest {

    private final BookingFilter bookingFilter = new BookingFilter();
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void testIsValidBooking_All() {
        BookingDto booking = createBookingDto(Status.WAITING, now.minusDays(1), now.plusDays(1));
        assertTrue(bookingFilter.isValidBooking(booking, State.ALL));
    }

    @Test
    void testIsValidBooking_Waiting() {
        BookingDto booking = createBookingDto(Status.WAITING, now.plusDays(1), now.plusDays(2));
        assertTrue(bookingFilter.isValidBooking(booking, State.WAITING));

        booking = createBookingDto(Status.APPROVED, now.plusDays(1), now.plusDays(2));
        assertFalse(bookingFilter.isValidBooking(booking, State.WAITING));
    }

    @Test
    void testIsValidBooking_Rejected() {
        BookingDto booking = createBookingDto(Status.REJECTED, now.plusDays(1), now.plusDays(2));
        assertTrue(bookingFilter.isValidBooking(booking, State.REJECTED));

        booking = createBookingDto(Status.APPROVED, now.plusDays(1), now.plusDays(2));
        assertFalse(bookingFilter.isValidBooking(booking, State.REJECTED));
    }

    @Test
    void testIsValidBooking_Current() {
        BookingDto booking = createBookingDto(Status.APPROVED, now.minusDays(1), now.plusDays(1));
        assertTrue(bookingFilter.isValidBooking(booking, State.CURRENT));

        booking = createBookingDto(Status.APPROVED, now.plusDays(1), now.plusDays(2));
        assertFalse(bookingFilter.isValidBooking(booking, State.CURRENT));

        booking = createBookingDto(Status.APPROVED, now.minusDays(2), now.minusDays(1));
        assertFalse(bookingFilter.isValidBooking(booking, State.CURRENT));
    }

    @Test
    void testIsValidBooking_Past() {
        BookingDto booking = createBookingDto(Status.APPROVED, now.minusDays(2), now.minusDays(1));
        assertTrue(bookingFilter.isValidBooking(booking, State.PAST));

        booking = createBookingDto(Status.APPROVED, now.plusDays(1), now.plusDays(2));
        assertFalse(bookingFilter.isValidBooking(booking, State.PAST));
    }

    @Test
    void testIsValidBooking_Future() {
        BookingDto booking = createBookingDto(Status.APPROVED, now.plusDays(1), now.plusDays(2));
        assertTrue(bookingFilter.isValidBooking(booking, State.FUTURE));

        booking = createBookingDto(Status.APPROVED, now.minusDays(2), now.minusDays(1));
        assertFalse(bookingFilter.isValidBooking(booking, State.FUTURE));
    }

    private BookingDto createBookingDto(Status status, LocalDateTime start, LocalDateTime end) {
        return BookingDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .status(status)
                .build();
    }
}
