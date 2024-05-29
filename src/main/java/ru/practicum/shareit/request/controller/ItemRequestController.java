package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.exception.PaginationSizeException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestResponse;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.request.validation.NonNegativeInteger;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.ITEM_REQUEST_PATH)
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestResponse addRequest(@Valid @RequestBody ItemRequest itemRequest,
                                          @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestService.addRequest(itemRequest, userId);
    }

    @GetMapping
    public Collection<ItemRequestResponse> getOwnRequests(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestService.getOwnRequests(userId);
    }

    @GetMapping(ApiPathConstants.ALL_PATH)
    public Collection<ItemRequestResponse> getRequests(@NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                                       @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size) {
        validatePaginationSize(from, size);
        return itemRequestService.getRequests(from, size);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public ItemRequestResponse getRequestById(@PathVariable @Positive Long id) {
        return itemRequestService.getRequestById(id);
    }

    private void validatePaginationSize(Integer from, Integer size) {
        if(size != null && from != null) {
            if (size < from) {
                throw new PaginationSizeException(from, size);
            }
        }
    }

}
