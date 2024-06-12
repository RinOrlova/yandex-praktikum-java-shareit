package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.booking.model.BookingDto;

import java.time.LocalDateTime;

@Component
public class BookingFilter {

    public boolean isValidBooking(BookingDto bookingDto, State state) {
        LocalDateTime nowTimeStamp = LocalDateTime.now();
        switch (state) {
            case ALL:
                return true;
            case WAITING:
                return bookingDto.getStatus() == Status.WAITING;
            case REJECTED:
                return bookingDto.getStatus() == Status.REJECTED;
            case CURRENT:
                return !bookingDto.getStart().isAfter(nowTimeStamp) && !bookingDto.getEnd().isBefore(nowTimeStamp);
            case PAST:
                return bookingDto.getEnd().isBefore(nowTimeStamp);
            case FUTURE:
                return bookingDto.getStart().isAfter(nowTimeStamp);
            default:
                return false;
        }
    }

}
