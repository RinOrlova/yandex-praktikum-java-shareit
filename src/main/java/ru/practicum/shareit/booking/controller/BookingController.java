package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;
import ru.practicum.shareit.booking.service.BookingService;
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

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse addBooking(@Valid @RequestBody BookingRequest booking,
                                      @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingService.addBooking(booking, userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public BookingResponse updateBookingStatus(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                               @PathVariable @Positive Long id,
                                               @RequestParam("approved") boolean isApproved) {
        return bookingService.updateBooking(userId, id, isApproved);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public BookingResponse getBookingById(@PathVariable @Positive Long id,
                                          @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return bookingService.getBookingByIdAndUserId(id, userId);
    }

    @GetMapping
    public Collection<BookingResponse> getBookingsByUser(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                         @RequestParam(value = "state", defaultValue = "ALL", required = false) @ValidStateEnum(enumClass = State.class) String state,
                                                         @NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                         @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size) {
        return bookingService.getBookingsByUser(userId, State.valueOf(state), from, size);
    }

    @GetMapping(ApiPathConstants.OWNER_PATH)
    public Collection<BookingResponse> getBookingsByOwner(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                                          @RequestParam(value = "state", defaultValue = "ALL", required = false) @ValidStateEnum(enumClass = State.class) String state,
                                                          @NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                          @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size) {
        return bookingService.getBookingsByOwnerId(userId, State.valueOf(state), from, size);
    }
}
