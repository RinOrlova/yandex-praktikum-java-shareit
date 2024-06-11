package ru.practicum.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;

import java.util.Collection;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {

    public BookingClient(@Value("${shareit-server.url}") String serverUrl,
                         RestTemplateBuilder builder) {
        super(serverUrl, builder, ApiPathConstants.BOOKING_PATH);
    }

    public ResponseEntity<Object> addBooking(BookingRequest bookingRequest, Long userId) {
        return post("", userId, bookingRequest);
    }

    public ResponseEntity<Object> updateBooking(Long userId, Long id, boolean isApproved) {
        Map<String, Object> parameters = Map.of(
                "approved", isApproved
        );
        return patch("/" + id, userId, parameters, null);
    }

    public ResponseEntity<Object> getBookingByIdAndUserId(Long id, Long userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> getBookingsByUser(Long userId, State state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getBookingsByOwnerId(Long userId, State state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}
