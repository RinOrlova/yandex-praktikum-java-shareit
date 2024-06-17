package ru.practicum.shareit.exception;

public class BookingNotFoundException extends AbstractNotFoundException {
    private static final String DEFAULT_MESSAGE = "Booking by id=%s not found";

    public BookingNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
