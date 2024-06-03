package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.CommentResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAdd() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Item description")
                .available(false)
                .build();

        when(itemService.add(any(Item.class), anyLong())).thenReturn(item);

        mockMvc.perform(post(ApiPathConstants.ITEM_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()));

        verify(itemService, times(1)).add(any(Item.class), anyLong());
    }

    @Test
    void testAddComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .text("Test comment")
                .build();
        CommentResponse commentResponse = CommentResponse.builder()
                .id(1L)
                .text("Test comment")
                .build();

        when(itemService.addComment(any(CommentRequest.class), anyLong(), anyLong())).thenReturn(commentResponse);

        mockMvc.perform(post(ApiPathConstants.ITEM_PATH + ApiPathConstants.COMMENT_PATH, 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.text").value(commentResponse.getText()));

        verify(itemService, times(1)).addComment(any(CommentRequest.class), anyLong(), anyLong());
    }

    @Test
    void testUpdate() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .build();

        when(itemService.update(any(Item.class), anyLong(), anyLong())).thenReturn(item);

        mockMvc.perform(patch(ApiPathConstants.ITEM_PATH + ApiPathConstants.BY_ID_PATH, 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()));

        verify(itemService, times(1)).update(any(Item.class), anyLong(), anyLong());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(itemService).delete(anyLong());

        mockMvc.perform(delete(ApiPathConstants.ITEM_PATH + ApiPathConstants.BY_ID_PATH, 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk());

        verify(itemService, times(1)).delete(anyLong());
    }

    @Test
    void testGetAllItems() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .build();


        when(itemService.getItems(anyLong())).thenReturn(Collections.singletonList(item));

        mockMvc.perform(get(ApiPathConstants.ITEM_PATH)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].name").value(item.getName()));

        verify(itemService, times(1)).getItems(anyLong());
    }

    @Test
    void testGetItemById() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .build();


        when(itemService.getItemByIdForUser(anyLong(), anyLong())).thenReturn(item);

        mockMvc.perform(get(ApiPathConstants.ITEM_PATH + ApiPathConstants.BY_ID_PATH, 1L)
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()));

        verify(itemService, times(1)).getItemByIdForUser(anyLong(), anyLong());
    }

    @Test
    void testSearch() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .build();


        when(itemService.search(anyString())).thenReturn(Collections.singletonList(item));

        mockMvc.perform(get(ApiPathConstants.ITEM_PATH + ApiPathConstants.SEARCH_PATH)
                        .param("text", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].name").value(item.getName()));

        verify(itemService, times(1)).search(anyString());
    }
}
