package ru.practicum.shareit.exception;

public class PaginationSizeException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Parameter 'size'=%s must be greater than or equal to parameter 'from'=%s";

    public PaginationSizeException(Integer size, Integer from) {
        super(String.format(MESSAGE_TEMPLATE, size, from));
    }
}
