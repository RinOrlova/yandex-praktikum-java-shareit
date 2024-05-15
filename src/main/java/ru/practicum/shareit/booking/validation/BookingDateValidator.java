package ru.practicum.shareit.booking.validation;

import ru.practicum.shareit.booking.model.Booking;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingDateValidator implements ConstraintValidator<ValidBookingDate, Booking> {

    @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        if (booking.getStart() == null || booking.getEnd() == null) {
            return true;
        }
        return booking.getEnd().isAfter(booking.getStart());
    }
}
