package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final InvalidDataInRequestException exception) {
        return new ErrorResponse("Invalid data in request.", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemUnavailableException(final ItemUnavailableException exception) {
        return new ErrorResponse("Item unavailable.", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnableToChangeBookingStatusException(final UnableToChangeBookingStatusException exception) {
        return new ErrorResponse("Unable to change status.", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAddCommentForbiddenException(final AddCommentForbiddenException exception) {
        return new ErrorResponse("Not possible to add a comment.", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Invalid request");
        return new ErrorResponse(message, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final AbstractNotFoundException exception) {
        return new ErrorResponse("Entity not found", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleForbiddenException(final ForbiddenException exception) {
        return new ErrorResponse("Access denied", exception.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownException(final RuntimeException exception) {
        return new ErrorResponse("Unexpected error", exception.getMessage());
    }
}
