package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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
                                       @PathVariable @Positive Long id,
                                       @RequestParam("approved") boolean isApproved) {
        return bookingService.updateBooking(userId, id, isApproved);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Booking getBookingById(@PathVariable @Positive Long id,
                                  @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingService.getBookingById(id, userId);
    }

    @GetMapping
    public Collection<Booking> getBookingsByUser(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                 @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.getBookingsByUser(userId, State.valueOf(state));
    }

    @GetMapping(ApiPathConstants.OWNER_PATH)
    public Collection<Booking> getBookingsByOwner(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                  @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.getBookingsByOwnerId(userId, State.valueOf(state));
    }
}
