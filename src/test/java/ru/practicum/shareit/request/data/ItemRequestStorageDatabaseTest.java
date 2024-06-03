package ru.practicum.shareit.request.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
class ItemRequestStorageDatabaseTest {

    @Autowired
    private ItemRequestRepository requestRepository;

    @MockBean
    private ItemRequestMapper requestMapper;

    @InjectMocks
    private ItemRequestStorageDatabase itemRequestStorageDatabase;

    private ItemRequestEntity itemRequestEntity;
    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemRequestEntity = new ItemRequestEntity();
        itemRequestEntity.setId(1L);
        itemRequestEntity.setDescription("Request description");
        itemRequestEntity.setCreated(LocalDateTime.now());

        itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("Request description")
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    void testAdd() {
        when(requestMapper.mapItemRequestDto2ItemRequestEntity(any(ItemRequestDto.class))).thenReturn(itemRequestEntity);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class))).thenReturn(itemRequestDto);
        when(requestRepository.saveAndFlush(any(ItemRequestEntity.class))).thenReturn(itemRequestEntity);

        ItemRequestDto savedDto = itemRequestStorageDatabase.add(itemRequestDto);

        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isEqualTo(itemRequestDto.getId());
        assertThat(savedDto.getDescription()).isEqualTo(itemRequestDto.getDescription());

        verify(requestRepository, times(1)).saveAndFlush(any(ItemRequestEntity.class));
        verify(requestMapper, times(1)).mapItemRequestDto2ItemRequestEntity(any(ItemRequestDto.class));
        verify(requestMapper, times(1)).mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class));
    }

    @Test
    void testGetAllItemRequestsByRequestorId() {
        when(requestRepository.getItemRequestEntitiesByRequestor_Id(anyLong())).thenReturn(Collections.singletonList(itemRequestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class))).thenReturn(itemRequestDto);

        Collection<ItemRequestDto> itemRequests = itemRequestStorageDatabase.getAllItemRequestsByRequestorId(1L);

        assertThat(itemRequests).isNotNull();
        assertThat(itemRequests.size()).isEqualTo(1);
        assertThat(itemRequests.iterator().next().getId()).isEqualTo(itemRequestDto.getId());

        verify(requestRepository, times(1)).getItemRequestEntitiesByRequestor_Id(anyLong());
        verify(requestMapper, times(1)).mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class));
    }

    @Test
    void testGetById() {
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class))).thenReturn(itemRequestDto);

        Optional<ItemRequestDto> optionalItemRequest = itemRequestStorageDatabase.getById(1L);

        assertThat(optionalItemRequest).isPresent();
        assertThat(optionalItemRequest.get().getId()).isEqualTo(itemRequestDto.getId());

        verify(requestRepository, times(1)).findById(anyLong());
        verify(requestMapper, times(1)).mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class));
    }

    @Test
    void testGetAllItemsFromTo() {
        Page<ItemRequestEntity> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(itemRequestEntity));
        when(requestRepository.findAllExcludingRequestorId(anyLong(), any(Pageable.class))).thenReturn(page);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class))).thenReturn(itemRequestDto);

        Collection<ItemRequestDto> itemRequests = itemRequestStorageDatabase.getAllItemsFromTo(0, 1, 1L);

        assertThat(itemRequests).isNotNull();
        assertThat(itemRequests.size()).isEqualTo(1);
        assertThat(itemRequests.iterator().next().getId()).isEqualTo(itemRequestDto.getId());

        verify(requestRepository, times(1)).findAllExcludingRequestorId(anyLong(), any(Pageable.class));
        verify(requestMapper, times(1)).mapItemRequestEntity2ItemRequestDto(any(ItemRequestEntity.class));
    }
}
