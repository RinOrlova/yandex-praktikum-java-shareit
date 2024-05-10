package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.data.UserEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemStorageDatabase implements ItemStorage {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto add(ItemDto itemDto) {
        ItemEntity entity = itemMapper.itemDtoToItemEntity(itemDto);
        ItemEntity savedEntity = itemRepository.save(entity);
        return itemMapper.itemEntityToItemDto(savedEntity);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        ItemEntity entity = itemMapper.itemDtoToItemEntity(itemDto);
        ItemEntity savedEntity = itemRepository.save(entity);
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
        UserEntity userEntity = itemMapper.userIdToUserDto(ownerId);
        return itemRepository.getAllByUserDto(userEntity)
                .stream()
                .map(itemMapper::itemEntityToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> search(String text) {
        return itemRepository.getAllByNameContainsIgnoreCase(text)
                .stream()
                .map(itemMapper::itemEntityToItemDto)
                .collect(Collectors.toList());
    }
}
