package ru.practicum.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.client.ItemRequestClient;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.validation.NonNegativeInteger;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.ITEM_REQUEST_PATH)
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@Valid @RequestBody ItemRequest itemRequest,
                                             @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestClient.addRequest(itemRequest, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnRequests(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestClient.getOwnRequests(userId);
    }

    @GetMapping(ApiPathConstants.ALL_PATH)
    public ResponseEntity<Object> getRequests(@NonNegativeInteger @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                              @NonNegativeInteger @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
                                              @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestClient.getRequests(from, size, userId);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public ResponseEntity<Object> getRequestById(@PathVariable @Positive Long id,
                                                 @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestClient.getRequestById(id, userId);
    }

}
