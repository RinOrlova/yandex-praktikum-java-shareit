package ru.practicum.shareit.item;

public class ItemNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Item by id=%s not found";

    public ItemNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
