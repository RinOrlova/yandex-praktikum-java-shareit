package ru.practicum.shareit.exception;

public class AddCommentForbiddenException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Comment creation forbidden.";

    public AddCommentForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

}
