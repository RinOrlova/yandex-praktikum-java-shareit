package ru.practicum.shareit.request.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestMapperTest {
    @InjectMocks
    private ItemRequestMapper itemRequestMapper = new ItemRequestMapperImpl();
    @Mock
    private UserMapper userMapper = mock(UserMapper.class);

    @Test
    void testMapContext() {

        ItemRequest itemRequest = ItemRequest.builder().description("description").build();
        when(userMapper.userEntity2UserDto(any())).thenReturn(UserDto.builder().id(1L).build());
        ItemRequestDto itemRequestDto = itemRequestMapper.mapContext(itemRequest, 1L);
        assertEquals("description", itemRequestDto.getDescription());
        assertEquals(1L, itemRequestDto.getRequestor().getId());
    }
}