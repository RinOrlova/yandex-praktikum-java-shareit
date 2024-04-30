package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.ITEM_PATH)
public class ItemController {

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;
    private final UserStorage userStorage;

    @PostMapping
    public Item add(@RequestHeader(value = X_SHARER_USER_ID) Long userId, @Valid @RequestBody Item item) {
        if(userStorage.getById(userId).isPresent()) {
            return itemService.add(item, userId);
        }
        throw new UserNotFoundException(userId);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public Item update(@RequestHeader(value = X_SHARER_USER_ID) Long userId, @PathVariable @Positive Long id,
                       @RequestBody Item item) {
        if(userStorage.getById(userId).isPresent()) {
            return itemService.update(item, userId);
        }
        throw new UserNotFoundException(userId);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void delete(@PathVariable @Positive Long id) {
        itemService.delete(id);
    }

    @GetMapping
    public Collection<Item> getAllItems() {
        return itemService.getItems();
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Item getItemById(@PathVariable @Positive Long id) {
        return itemService.getItemById(id);
    }
}
