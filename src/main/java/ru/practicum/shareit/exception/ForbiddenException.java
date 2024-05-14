package ru.practicum.shareit.exception;

public class ForbiddenException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Action forbidden";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }
}
