package ru.practicum.shareit.exception;

public class ItemUnavailableException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Item with id=%s unavailable";

    public ItemUnavailableException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
