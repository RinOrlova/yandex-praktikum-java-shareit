package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {

    Item add(Item item, Long userId);

    Item update(Item item, Long userId);

    void delete(Long id);

    List<Item> getItems();

    Item getItemById(Long id);


}
