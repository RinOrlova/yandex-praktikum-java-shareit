package ru.practicum.shareit.item.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.data.User;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
    public ItemDto add(ItemDto itemDto) {
        Item entity = itemMapper.itemDtoToItemEntity(itemDto);
        Item savedEntity = itemRepository.saveAndFlush(entity);
        return itemMapper.itemEntityToItemDto(savedEntity);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        Item entity = itemMapper.itemDtoToItemEntity(itemDto);
        Item savedEntity = itemRepository.saveAndFlush(entity);
        return itemMapper.itemEntityToItemDto(savedEntity);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Collection<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::itemEntityToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDto> getById(Long id) {
        return itemRepository.findById(id)
                .map(itemMapper::itemEntityToItemDto);
    }

    @Override
    public Collection<ItemDto> getAllByUserId(Long ownerId) {
        User user = userMapper.userIdToUserDto(ownerId);
        return itemRepository.getAllByUser(user)
                .stream()
                .map(itemMapper::itemEntityToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> search(String text) {
        return itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text)
                .stream()
                .map(itemMapper::itemEntityToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean userHasBookedItem(Long itemId, Long userId) {
        return itemRepository.existsByItemIdAndUserIdWithPastBookings(itemId, userId);
    }
}
