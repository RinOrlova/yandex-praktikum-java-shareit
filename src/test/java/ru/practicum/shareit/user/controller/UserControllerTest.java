package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post(ApiPathConstants.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("Updated User")
                .email("updated@example.com")
                .build();

        when(userService.updateUser(any(User.class), anyLong())).thenReturn(user);

        mockMvc.perform(patch(ApiPathConstants.USER_PATH + ApiPathConstants.BY_ID_PATH, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

        verify(userService, times(1)).updateUser(any(User.class), anyLong());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mockMvc.perform(delete(ApiPathConstants.USER_PATH + ApiPathConstants.BY_ID_PATH, 1L))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(anyLong());
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        when(userService.getUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get(ApiPathConstants.USER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].name").value(user.getName()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get(ApiPathConstants.USER_PATH + ApiPathConstants.BY_ID_PATH, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

        verify(userService, times(1)).getUserById(anyLong());
    }
}
