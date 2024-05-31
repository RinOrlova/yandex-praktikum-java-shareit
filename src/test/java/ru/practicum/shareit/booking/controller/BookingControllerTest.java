package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void test_addBooking_badRequest_dateInPast() throws Exception {
        var bookingRequest = BookingRequest.builder()
                .start(LocalDateTime.now().minusDays(2L))
                .end(LocalDateTime.now().minusDays(1L))
                .itemId(1L)
                .build();

        mockMvc.perform(post(ApiPathConstants.BOOKING_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());

        verify(bookingService, never()).addBooking(any(), any());
    }
}