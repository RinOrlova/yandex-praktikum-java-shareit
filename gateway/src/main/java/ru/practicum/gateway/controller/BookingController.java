package ru.practicum.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.client.BookingClient;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;
import ru.practicum.shareit.booking.validation.ValidStateEnum;
import ru.practicum.shareit.request.validation.NonNegativeInteger;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.BOOKING_PATH)
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addBooking(@Valid @RequestBody BookingRequest booking,
                                             @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingClient.addBooking(booking, userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public ResponseEntity<Object> updateBookingStatus(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                      @PathVariable @Positive Long id,
                                                      @RequestParam("approved") boolean isApproved) {
        return bookingClient.updateBooking(userId, id, isApproved);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public ResponseEntity<Object> getBookingById(@PathVariable @Positive Long id,
                                                 @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingClient.getBookingByIdAndUserId(id, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingsByUser(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                         @RequestParam(value = "state", defaultValue = "ALL", required = false) @ValidStateEnum(enumClass = State.class) String state,
                                                         @NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                         @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size) {
        return bookingClient.getBookingsByUser(userId, State.valueOf(state), from, size);
    }

    @GetMapping(ApiPathConstants.OWNER_PATH)
    public ResponseEntity<Object> getBookingsByOwner(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                          @RequestParam(value = "state", defaultValue = "ALL", required = false) @ValidStateEnum(enumClass = State.class) String state,
                                                          @NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                          @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size) {
        return bookingClient.getBookingsByOwnerId(userId, State.valueOf(state), from, size);
    }
}
