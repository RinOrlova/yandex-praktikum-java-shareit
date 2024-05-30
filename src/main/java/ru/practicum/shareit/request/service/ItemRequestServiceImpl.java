package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.data.ItemRequestStorage;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestResponse;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestStorage itemRequestStorage;
    private final ItemRequestMapper itemRequestMapper;
    private final UserService userService;

    @Override
    public ItemRequestResponse addRequest(ItemRequest itemRequest, Long userId) {
        // check if user exists
        userService.getUserById(userId);
        ItemRequestDto itemRequestDto = itemRequestMapper.mapItemRequest2ItemRequestDto(itemRequest, userId);
        ItemRequestDto itemRequestDtoFromStorage = itemRequestStorage.add(itemRequestDto);
        return itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDtoFromStorage);
    }

    @Override
    public Collection<ItemRequestResponse> getOwnRequests(Long userId) {
        userService.getUserById(userId);
        Collection<ItemRequestDto> allItemRequestDtosForUser = itemRequestStorage.getAllItemRequestsByRequestorId(userId);
        return allItemRequestDtosForUser.stream()
                .map(itemRequestMapper::mapItemRequestDto2ItemRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemRequestResponse> getRequests(int from, int size, Long userId) {
        userService.getUserById(userId);
        Collection<ItemRequestDto> itemRequestDtosSegment = itemRequestStorage.getAllItemsFromTo(from, size, userId);
        return itemRequestDtosSegment.stream()
                .map(itemRequestMapper::mapItemRequestDto2ItemRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestResponse getRequestById(Long id, Long userId) {
        if(userId != null){
            userService.getUserById(userId);
        }
        ItemRequestDto itemRequestDto = itemRequestStorage.getById(id)
                .orElseThrow(() -> new ItemRequestNotFoundException(id));
        return itemRequestMapper.mapItemRequestDto2ItemRequestResponse(itemRequestDto);
    }
}
