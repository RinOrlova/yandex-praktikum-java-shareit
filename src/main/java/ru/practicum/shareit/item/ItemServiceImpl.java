package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemStorage itemStorage;
    private final ItemConverter itemConverter;

    @Override
    public Item add(Item item, Long userId) {
        ItemDto itemDto = itemConverter.convertItemToItemDto(item, userId);
        ItemDto itemFromStorage = itemStorage.add(itemDto);
        return itemConverter.convertItemDtoToItem(itemFromStorage);
    }

    @Override
    public Item update(Item item, Long userId) {
        ItemDto itemDto = itemConverter.convertItemToItemDto(item, userId);
        ItemDto itemFromStorage = itemStorage.update(itemDto);
        return itemConverter.convertItemDtoToItem(itemFromStorage);
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }

    @Override
    public List<Item> getItems() {
        Collection<ItemDto> itemsFromStorage = itemStorage.getAll();
        return itemsFromStorage.stream()
                .map(itemConverter::convertItemDtoToItem)
                .collect(Collectors.toList());
    }

    @Override
    public Item getItemById(Long id) {
        ItemDto itemFromStorage = itemStorage.getById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        return itemConverter.convertItemDtoToItem(itemFromStorage);
    }
}
