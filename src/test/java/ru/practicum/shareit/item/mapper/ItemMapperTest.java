package ru.practicum.shareit.item.mapper;


import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.request.data.ItemRequestEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @Test
    void testIdToItemRequestEntity() {
        Long input = 1L;
        ItemRequestEntity itemRequestEntity = itemMapper.idToItemRequestEntity(input);
        assertEquals(input, itemRequestEntity.getId());
    }

    @Test
    void testMapContext() {
        Item item = Item.builder()
                .available(true)
                .name("name")
                .description("description")
                .requestId(1L)
                .build();
        ItemDto itemDto = itemMapper.mapContext(item, 3L);
        assertEquals("name", itemDto.getName());
        assertEquals("description", itemDto.getDescription());
        assertEquals(1L, itemDto.getRequestId());
        assertEquals(3L, itemDto.getOwnerId());

    }
}