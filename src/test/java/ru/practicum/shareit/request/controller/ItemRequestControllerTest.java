package ru.practicum.shareit.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestResponse;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRequestService itemRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddRequest() throws Exception {
        ItemRequest itemRequest = ItemRequest.builder()
                .description("Test Request")
                .build();

        ItemRequestResponse itemRequestResponse = ItemRequestResponse.builder()
                .id(1L)
                .description("Test Request")
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.addRequest(any(ItemRequest.class), anyLong())).thenReturn(itemRequestResponse);

        mockMvc.perform(post(ApiPathConstants.ITEM_REQUEST_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestResponse.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestResponse.getDescription()));

        verify(itemRequestService, times(1)).addRequest(any(ItemRequest.class), anyLong());
    }

    @Test
    void testGetOwnRequests() throws Exception {
        ItemRequestResponse itemRequestResponse = ItemRequestResponse.builder()
                .id(1L)
                .description("Test Request")
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.getOwnRequests(anyLong())).thenReturn(Collections.singletonList(itemRequestResponse));

        mockMvc.perform(get(ApiPathConstants.ITEM_REQUEST_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestResponse.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestResponse.getDescription()));

        verify(itemRequestService, times(1)).getOwnRequests(anyLong());
    }

    @Test
    void testGetRequests() throws Exception {
        ItemRequestResponse itemRequestResponse = ItemRequestResponse.builder()
                .id(1L)
                .description("Test Request")
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.getRequests(anyInt(), anyInt(), anyLong())).thenReturn(Collections.singletonList(itemRequestResponse));

        mockMvc.perform(get(ApiPathConstants.ITEM_REQUEST_PATH + ApiPathConstants.ALL_PATH)
                        .param("from", "0")
                        .param("size", "10")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestResponse.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestResponse.getDescription()));

        verify(itemRequestService, times(1)).getRequests(anyInt(), anyInt(), anyLong());
    }

    @Test
    void testGetRequestById() throws Exception {
        ItemRequestResponse itemRequestResponse = ItemRequestResponse.builder()
                .id(1L)
                .description("Test Request")
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.getRequestById(anyLong(), anyLong())).thenReturn(itemRequestResponse);

        mockMvc.perform(get(ApiPathConstants.ITEM_REQUEST_PATH + ApiPathConstants.BY_ID_PATH, 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestResponse.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestResponse.getDescription()));

        verify(itemRequestService, times(1)).getRequestById(anyLong(), anyLong());
    }
}
