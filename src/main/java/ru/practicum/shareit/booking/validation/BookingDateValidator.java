package ru.practicum.shareit.booking.validation;

import ru.practicum.shareit.booking.model.BookingRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingDateValidator implements ConstraintValidator<ValidBookingDate, BookingRequest> {

    @Override
    public boolean isValid(BookingRequest booking, ConstraintValidatorContext context) {
        if (booking.getStart() == null || booking.getEnd() == null) {
            return true;
        }
        return booking.getEnd().isAfter(booking.getStart());
    }
}
