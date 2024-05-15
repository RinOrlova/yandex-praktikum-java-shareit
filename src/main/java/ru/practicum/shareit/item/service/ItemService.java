package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    Item add(Item item, Long userId);

    Item update(Item item, Long id, Long userId);

    void delete(Long id);

    List<Item> getItems(Long userId);

    Item getItemById(Long id);

    Collection<Item> search(String text);

    Long getOwnerForItemByItemId(Long itemId);

    boolean isItemAvailable(Long itemId);
}
