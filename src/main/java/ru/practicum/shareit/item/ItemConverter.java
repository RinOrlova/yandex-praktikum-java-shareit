package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

@Component
public class ItemConverter {

    public ItemDto convertItemToItemDto(Item item, Long userId) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .userId(userId)
                .available(item.getAvailable())
                .request(item.getRequest())
                .build();
    }

    public Item convertItemDtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequest())
                .build();
    }

}
