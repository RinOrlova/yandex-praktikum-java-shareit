package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_addBooking() throws Exception {
        var bookingRequest = BookingRequest.builder()
                .start(LocalDateTime.now().plusDays(1L))
                .end(LocalDateTime.now().plusDays(2L))
                .itemId(1L)
                .build();

        mockMvc.perform(post(ApiPathConstants.BOOKING_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).addBooking(bookingRequest, 1L);
    }

    @Test
    void test_update_booking_status() throws Exception {

        mockMvc.perform(patch(ApiPathConstants.BOOKING_PATH + "/" + 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).updateBooking(1L, 1L, true);
    }

    @Test
    void test_get_booking_by_id() throws Exception {
        mockMvc.perform(get(ApiPathConstants.BOOKING_PATH + "/" + 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).getBookingByIdAndUserId(1L, 1L);
    }

    @Test
    void test_get_bookings_by_user() throws Exception {
        mockMvc.perform(get(ApiPathConstants.BOOKING_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).getBookingsByUser(1L, State.ALL, 0, 100);
    }

    @Test
    void test_get_bookings_by_owner_id() throws Exception {
        mockMvc.perform(get(ApiPathConstants.BOOKING_PATH + ApiPathConstants.OWNER_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).getBookingsByOwnerId(1L, State.ALL, 0, 100);
    }

}