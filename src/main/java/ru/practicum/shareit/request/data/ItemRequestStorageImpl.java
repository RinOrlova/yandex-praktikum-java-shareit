package ru.practicum.shareit.request.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRequestStorageImpl implements ItemRequestStorage {

    private final ItemRequestRepository requestRepository;
    private final ItemRequestMapper requestMapper;

    @Override
    public ItemRequestDto add(ItemRequestDto requestDto) {
        ItemRequest itemRequest = requestMapper.mapItemRequestDto2ItemRequestEntity(requestDto);
        ItemRequest itemRequestFromStorage = requestRepository.saveAndFlush(itemRequest);
        return requestMapper.mapItemRequestEntity2ItemRequestDto(itemRequestFromStorage);
    }

    @Override
    public Collection<ItemRequestDto> getAllItemRequestsByRequestorId(Long requestorId) {
        Collection<ItemRequest> itemRequestEntities = requestRepository.getItemRequestEntitiesByRequestor_Id(requestorId);
        return itemRequestEntities.stream()
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getById(Long id) {
        return requestRepository.findById(id)
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .orElseThrow(() -> new ItemRequestNotFoundException(id));
    }

    @Override
    public Collection<ItemRequestDto> getAllItemsFromTo(int pageNumber, int size, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<ItemRequest> itemRequestEntityPage = requestRepository.findAllExcludingRequestorId(userId, pageable);
        return itemRequestEntityPage.stream()
                .map(requestMapper::mapItemRequestEntity2ItemRequestDto)
                .collect(Collectors.toList());
    }
}
