package ru.practicum.shareit.exception;

public class UnableToChangeBookingStatusException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Unable to change status.";

    public UnableToChangeBookingStatusException() {
        super(DEFAULT_MESSAGE);
    }
}
