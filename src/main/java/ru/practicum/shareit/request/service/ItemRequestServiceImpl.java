package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestResponse;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    @Override
    public ItemRequestResponse addRequest(ItemRequest itemRequest, Long userId) {
        return null;
    }

    @Override
    public Collection<ItemRequestResponse> getOwnRequests(Long userId) {
        return null;
    }

    @Override
    public Collection<ItemRequestResponse> getRequests(int from, int size) {
        return null;
    }

    @Override
    public ItemRequestResponse getRequestById(Long id) {
        return null;
    }
}
