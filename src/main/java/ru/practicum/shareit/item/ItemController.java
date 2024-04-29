package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;

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

    @GetMapping
    public Item add(@Valid @RequestBody Item item) {
        return itemService.add(item);
    }

    @PutMapping
    public Item update(@Valid @RequestBody Item item) {
        return itemService.update(item);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void delete(@PathVariable @Positive Long id) {
        itemService.delete(id);
    }

    @GetMapping
    public Collection<Item> getAll() {
        return itemService.getItems();
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public Item getItemById(@PathVariable @Positive Long id) {
        return itemService.getItemById(id);
    }
}
