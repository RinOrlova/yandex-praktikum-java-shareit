package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    @Override
    public ItemRequest addRequest(ItemRequest itemRequest, Long userId) {
        return null;
    }

    @Override
    public Collection<ItemRequest> getOwnRequests(Long userId) {
        return null;
    }

    @Override
    public Collection<ItemRequest> getRequests(int from, int size) {
        return null;
    }

    @Override
    public ItemRequest getRequestById(Long id) {
        return null;
    }
}
