package ru.practicum.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.client.ItemClient;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.ITEM_PATH)
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                      @Valid @RequestBody Item item) {
        return itemClient.add(item, userId);
    }

    @PostMapping(ApiPathConstants.COMMENT_PATH)
    public ResponseEntity<Object> addComment(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                             @PathVariable @Positive Long id,
                                             @Valid @RequestBody CommentRequest commentRequest) {
        return itemClient.addComment(commentRequest, id, userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public ResponseEntity<Object> update(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                                         @PathVariable @Positive Long id,
                                         @RequestBody Item item) {
        return itemClient.update(item, id, userId);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void delete(@PathVariable @Positive Long id) {
        itemClient.deleteItem(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemClient.getItems(userId);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public ResponseEntity<Object> getItemById(@PathVariable @Positive Long id,
                                              @RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemClient.getItemByIdForUser(id, userId);
    }

    @GetMapping(ApiPathConstants.SEARCH_PATH)
    public ResponseEntity<Object> search(@RequestParam(name = "text") String text) {
        return itemClient.search(text);
    }
}
