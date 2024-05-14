package ru.practicum.shareit.booking.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.BOOKING_PATH)
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking addBooking(@Valid @RequestBody Booking booking,
                              @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingService.addBooking(booking, userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public Booking updateBookingStatus(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                       @PathVariable @Positive Long bookingId,
                                       @RequestParam("isApproved") @NonNull boolean isApproved) {
        return bookingService.updateBooking(userId, bookingId, isApproved);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Booking getBookingById(@PathVariable @Positive Long bookingId,
                                  @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping
    public Collection<Booking> getBookingsByUser(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                 @RequestParam(value = "state", defaultValue = "ALL", required = false) State state) {
        return bookingService.getBookingsByUser(userId, state);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Collection<Booking> getBookingsByOwner(@PathVariable @Positive Long ownerId,
                                                  @RequestParam(value = "state", defaultValue = "ALL", required = false) State state) {
        return bookingService.getBookingsByOwnerId(ownerId, state);
    }
}
