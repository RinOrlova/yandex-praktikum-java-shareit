package ru.practicum.shareit.request.data;

import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.Collection;
import java.util.Optional;

public interface ItemRequestStorage {

    ItemRequestDto add(ItemRequestDto requestDto);
    Collection<ItemRequestDto> getOwn();
    Collection<ItemRequestDto> getAll();
    Optional<ItemRequestDto> getById(Long id);
}
