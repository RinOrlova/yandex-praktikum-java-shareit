package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

@Component
public class ItemConverter {

    public ItemDto convertItemToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .owner(item.getOwner())
                .available(item.getAvailable())
                .request(item.getRequest())
                .build();
    }

    public Item convertItemDtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .owner(itemDto.getOwner())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequest())
                .build();
    }

}
