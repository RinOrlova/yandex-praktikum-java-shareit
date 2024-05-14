package ru.practicum.shareit.item.data;

import ru.practicum.shareit.item.model.ItemDto;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {

    ItemDto add(ItemDto itemDto);

    ItemDto update(ItemDto itemDto);

    void delete(Long id);

    Collection<ItemDto> getAll();

    Optional<ItemDto> getById(Long id);

    Collection<ItemDto> getAllByUserId(Long ownerId);

    Collection<ItemDto> search(String text);
}
