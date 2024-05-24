package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

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
    public ItemRequest addRequest(@Valid @RequestBody ItemRequest itemRequest,
                                  @RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestService.addRequest(itemRequest, userId);
    }

    @GetMapping
    public Collection<ItemRequest> getOwnRequests(@RequestHeader(ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemRequestService.getOwnRequests(userId);
    }

    @GetMapping(ApiPathConstants.ALL_PATH)
    public Collection<ItemRequest> getRequests(@RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                               @RequestParam(value = "size", defaultValue = "100", required = false) int size) {
        if (from < 0) {
            throw new IllegalArgumentException("Parameter 'from' must be greater than or equal to 0");
        }
        if (size < from) {
            throw new IllegalArgumentException("Parameter 'size' must be greater than or equal to parameter 'from'");
        }
        return itemRequestService.getRequests(from, size);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public ItemRequest getRequestById(@PathVariable @Positive Long id) {
        return itemRequestService.getRequestById(id);
    }

}
