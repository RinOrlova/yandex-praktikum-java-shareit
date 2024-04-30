package ru.practicum.shareit.item;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {

    ItemDto add(ItemDto itemDto);

    ItemDto update(ItemDto itemDto);

    void delete(Long id);

    Collection<ItemDto> getAll();

    Optional<ItemDto> getById(Long id);

    Collection<ItemDto> search(String text);
}
