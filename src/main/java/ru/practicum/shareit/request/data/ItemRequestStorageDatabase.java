package ru.practicum.shareit.request.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRequestStorageDatabase implements ItemRequestStorage {

    private final ItemRequestRepository requestRepository;
    private final ItemRequestMapper requestMapper;

    @Override
    public ItemRequestDto add(ItemRequestDto requestDto) {
        ItemRequestEntity itemRequestEntity = requestMapper.mapItemRequestDto2ItemRequestEntity(requestDto);
        ItemRequestEntity itemRequestEntityFromStorage = requestRepository.saveAndFlush(itemRequestEntity);
        return requestMapper.mapItemRequestEntity2ItemRequestDto(itemRequestEntityFromStorage);
    }

    @Override
    public Collection<ItemRequestDto> getAllItemRequestsByRequestorId(Long requestorId) {
        Collection<ItemRequestEntity> itemRequestEntities = requestRepository.getItemRequestEntitiesByRequestor_Id(requestorId);
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

    @Override
    public Collection<ItemRequestDto> getAllItemsFromTo(int from, int size) {
        Page<ItemRequestEntity> itemRequestEntityPage = requestRepository.findAll(PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id")));
        return itemRequestEntityPage.stream()
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .collect(Collectors.toList());
    }
}
