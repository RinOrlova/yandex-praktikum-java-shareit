package ru.practicum.shareit.exception;

public class ForbiddenException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "You are not the owner of the item";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }
}
