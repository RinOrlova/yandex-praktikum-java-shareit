package ru.practicum.shareit.exception;

public class ItemRequestNotFoundException extends AbstractNotFoundException {

    private static final String DEFAULT_MESSAGE = "Item request by id=%s not found";

    public ItemRequestNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}