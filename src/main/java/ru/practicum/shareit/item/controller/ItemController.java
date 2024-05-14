package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.ITEM_PATH)
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item add(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                    @Valid @RequestBody Item item) {
        return itemService.add(item, userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public Item update(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId,
                       @PathVariable @Positive Long id,
                       @RequestBody Item item) {
        return itemService.update(item, id, userId);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void delete(@PathVariable @Positive Long id) {
        itemService.delete(id);
    }

    @GetMapping
    public Collection<Item> getAllItems(@RequestHeader(value = ApiPathConstants.X_SHARER_USER_ID) Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Item getItemById(@PathVariable @Positive Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping(ApiPathConstants.SEARCH_PATH)
    public Collection<Item> search(@RequestParam(name = "text") String text) {
        return itemService.search(text);
    }
}
