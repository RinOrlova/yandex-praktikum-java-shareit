package ru.practicum.shareit.request.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return itemRequestEntities.stream()
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemRequestDto> getById(Long id) {
        return requestRepository.findById(id)
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto);
    }

    @Override
    public Collection<ItemRequestDto> getAllItemsFromTo(int pageNumber, int size, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<ItemRequestEntity> itemRequestEntityPage = requestRepository.findAllExcludingRequestorId(userId, pageable);
        return itemRequestEntityPage.stream()
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .collect(Collectors.toList());
    }
}
