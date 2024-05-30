package ru.practicum.shareit.request.data;

import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.Collection;
import java.util.Optional;

public interface ItemRequestStorage {

    ItemRequestDto add(ItemRequestDto requestDto);
    Collection<ItemRequestDto> getAllItemRequestsByRequestorId(Long requestorId);
    Optional<ItemRequestDto> getById(Long id);
    Collection<ItemRequestDto> getAllItemsFromTo(int from, int size, Long userId);
}
