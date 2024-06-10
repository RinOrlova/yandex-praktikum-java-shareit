package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestResponse;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestResponse addRequest(ItemRequest itemRequest, Long userId);

    Collection<ItemRequestResponse> getOwnRequests(Long userId);

    Collection<ItemRequestResponse> getRequests(int from, int size, Long userId);

    ItemRequestResponse getRequestById(Long id, Long userId);

}
