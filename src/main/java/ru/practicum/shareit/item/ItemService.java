package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {

    Item add(Item item);

    Item update(Item item);

    void delete(Long id);

    List<Item> getItems();

    Item getItemById(Long id);


}
