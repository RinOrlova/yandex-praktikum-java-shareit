package ru.practicum.shareit.request.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemStorageDatabase implements ItemRequestStorage {

    private final ItemRequestRepository requestRepository;
    private final ItemRequestMapper requestMapper;
    @Override
    public ItemRequestDto add(ItemRequestDto requestDto) {
        return null;
    }

    @Override
    public Collection<ItemRequestDto> getOwn() {
        return null;
    }

    @Override
    public Collection<ItemRequestDto> getAll() {
        return null;
    }

    @Override
    public Optional<ItemRequestDto> getById(Long id) {
        return Optional.empty();
    }
}
