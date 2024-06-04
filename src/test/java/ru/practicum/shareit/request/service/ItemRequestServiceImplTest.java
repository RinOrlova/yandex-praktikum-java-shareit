package ru.practicum.shareit.request.service;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.data.ItemRequestStorage;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestResponse;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestStorage itemRequestStorage;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    private ItemRequest itemRequest;
    private ItemRequestDto itemRequestDto;
    private ItemRequestResponse itemRequestResponse;
    private User user;

    @BeforeEach
    void setUp() {
        itemRequest = ItemRequest.builder()
                .description("Test Request")
                .build();
        user = User.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();


        itemRequestResponse = ItemRequestResponse.builder()
                .id(1L)
                .description("Test Request")
                .created(LocalDateTime.now())
                .build();


        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("Test Request")
                .requestor(userDto)
                .build();

    }

    @Test
    public void testAddRequest() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);
        when(itemRequestMapper.mapItemRequest2ItemRequestDto(itemRequest, userId)).thenReturn(itemRequestDto);
        when(itemRequestStorage.add(itemRequestDto)).thenReturn(itemRequestDto);
        when(itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDto)).thenReturn(itemRequestResponse);

        ItemRequestResponse result = itemRequestService.addRequest(itemRequest, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(itemRequestResponse.getId());
        verify(userService, times(1)).getUserById(userId);
        verify(itemRequestMapper, times(1)).mapItemRequest2ItemRequestDto(itemRequest, userId);
        verify(itemRequestStorage, times(1)).add(itemRequestDto);
        verify(itemRequestMapper, times(1)).mapItemRequestDto2ItemRequestResponse(itemRequestDto);
    }

    @Test
    public void testGetOwnRequests() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);
        when(itemRequestStorage.getAllItemRequestsByRequestorId(userId)).thenReturn(Collections.singletonList(itemRequestDto));
        when(itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDto)).thenReturn(itemRequestResponse);

        Collection<ItemRequestResponse> result = itemRequestService.getOwnRequests(userId);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getId()).isEqualTo(itemRequestResponse.getId());
        verify(userService, times(1)).getUserById(userId);
        verify(itemRequestStorage, times(1)).getAllItemRequestsByRequestorId(userId);
        verify(itemRequestMapper, times(1)).mapItemRequestDto2ItemRequestResponse(itemRequestDto);
    }

    @Test
    public void testGetRequests() {
        int from = 0;
        int size = 10;
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);
        when(itemRequestStorage.getAllItemsFromTo(from, size, userId)).thenReturn(Collections.singletonList(itemRequestDto));
        when(itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDto)).thenReturn(itemRequestResponse);

        Collection<ItemRequestResponse> result = itemRequestService.getRequests(from, size, userId);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getId()).isEqualTo(itemRequestResponse.getId());
        verify(userService, times(1)).getUserById(userId);
        verify(itemRequestStorage, times(1)).getAllItemsFromTo(from, size, userId);
        verify(itemRequestMapper, times(1)).mapItemRequestDto2ItemRequestResponse(itemRequestDto);
    }

    @Test
    public void testGetRequestById() {
        Long requestId = 1L;
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);
        when(itemRequestStorage.getById(requestId)).thenReturn(Optional.of(itemRequestDto));
        when(itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDto)).thenReturn(itemRequestResponse);

        ItemRequestResponse result = itemRequestService.getRequestById(requestId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(itemRequestResponse.getId());
        verify(userService, times(1)).getUserById(userId);
        verify(itemRequestStorage, times(1)).getById(requestId);
        verify(itemRequestMapper, times(1)).mapItemRequestDto2ItemRequestResponse(itemRequestDto);
    }

    @Test
    public void testGetRequestById_NotFound() {
        Long requestId = 1L;
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);
        when(itemRequestStorage.getById(requestId)).thenReturn(Optional.empty());

        assertThrows(ItemRequestNotFoundException.class, () -> itemRequestService.getRequestById(requestId, userId));

        verify(userService, times(1)).getUserById(userId);
        verify(itemRequestStorage, times(1)).getById(requestId);
    }
}
