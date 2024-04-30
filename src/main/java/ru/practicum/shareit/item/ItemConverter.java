package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemConverter {

    public ItemDto convertItemToItemDto(Item item, Long userId) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .userId(userId)
                .available(Optional.ofNullable(item.getAvailable()).orElse(false))
                .request(item.getRequest())
                .build();
    }

    public Item convertItemDtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.isAvailable())
                .request(itemDto.getRequest())
                .build();
    }

}
