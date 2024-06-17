package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.data.ItemStorage;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemStorage itemStorage;
    @Mock
    private UserService userService;

    @Mock
    private ItemMapper itemMapper;

    @Test
    void itemNotFound() {
        when(itemStorage.getById(any())).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(1L));
    }

    @Test
    void testUpdate() {
        when(userService.getUserById(any())).thenReturn(User.builder().id(1L).name("name").email("email@email.com").build());
        ItemDto itemDto = ItemDto.builder()
                .available(true)
                .name("name")
                .description("description")
                .ownerId(1L)
                .build();
        when(itemStorage.getById(any())).thenReturn(Optional.of(itemDto));

        Item item = Item.builder()
                .name("itemName")
                .description("description")
                .available(true)
                .id(1L)
                .build();
        when(itemMapper.item2ItemDto(any(), any())).thenReturn(itemDto);
        when(itemStorage.update(any())).thenReturn(itemDto);
        when(itemMapper.itemDto2Item(any())).thenReturn(item);

        Item updatedItem = itemService.update(item, 1L, 1L);
        assertEquals("itemName", updatedItem.getName());
    }

    @Test
    void testForbiddenUpdate() {
        when(userService.getUserById(any())).thenReturn(User.builder().id(1L).name("name").email("email@email.com").build());
        ItemDto itemDto = ItemDto.builder()
                .available(true)
                .name("name")
                .description("description")
                .ownerId(2L)
                .build();
        when(itemStorage.getById(any())).thenReturn(Optional.of(itemDto));

        Item item = Item.builder()
                .name("itemName")
                .description("description")
                .available(true)
                .id(1L)
                .build();
        assertThrows(ForbiddenException.class, () -> itemService.update(item, 1L, 1L));
    }

    @Test
    void searchEmptyInput() {
        assertTrue(itemService.search(null).isEmpty());
        assertTrue(itemService.search("").isEmpty());
        assertTrue(itemService.search(" ").isEmpty());
    }
}