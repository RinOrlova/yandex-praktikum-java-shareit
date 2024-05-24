package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequest addRequest(ItemRequest itemRequest, Long userId);

    Collection<ItemRequest> getOwnRequests(Long userId);

    Collection<ItemRequest> getRequests(int from, int size);

    ItemRequest getRequestById(Long id);

}
