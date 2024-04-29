package ru.practicum.shareit.user;

public class UserNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "User by id=%s not found";


    public UserNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
