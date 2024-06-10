package ru.practicum.shareit.request.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.user.data.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestStorageImplTest {

    @Mock
    private ItemRequestRepository requestRepository;

    @Mock
    private ItemRequestMapper requestMapper;

    @InjectMocks
    private ItemRequestStorageImpl itemRequestStorageImpl;

    private ItemRequestDto requestDto;
    private ItemRequest requestEntity;

    @BeforeEach
    void setUp() {
        requestDto = ItemRequestDto.builder()
                .id(1L)
                .description("Test Request")
                .requestor(UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build())
                .build();

        requestEntity = new ItemRequest();
        requestEntity.setId(1L);
        requestEntity.setDescription("Test Request");
        User user = new User();
        user.setId(1L);
        user.setName("Thom Yorke");
        user.setEmail("thom.yorke@gmail.com");

        requestEntity.setRequestor(user);
    }

    @Test
    public void testAdd() {
        when(requestMapper.mapItemRequestDto2ItemRequestEntity(requestDto)).thenReturn(requestEntity);
        when(requestRepository.saveAndFlush(requestEntity)).thenReturn(requestEntity);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        ItemRequestDto result = itemRequestStorageImpl.add(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(requestDto.getId());
        assertThat(result.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    public void testGetAllItemRequestsByRequestorId() {
        when(requestRepository.getItemRequestEntitiesByRequestor_Id(1L)).thenReturn(Collections.singletonList(requestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        List<ItemRequestDto> result = (List<ItemRequestDto>) itemRequestStorageImpl.getAllItemRequestsByRequestorId(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(requestDto.getId());
    }

    @Test
    public void testGetById() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(requestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        ItemRequestDto result = itemRequestStorageImpl.getById(1L);

        assertThat(result.getId()).isEqualTo(requestDto.getId());
    }

    @Test
    public void testGetAllItemsFromTo() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<ItemRequest> page = new PageImpl<>(Collections.singletonList(requestEntity));
        when(requestRepository.findAllExcludingRequestorId(1L, pageable)).thenReturn(page);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        List<ItemRequestDto> result = (List<ItemRequestDto>) itemRequestStorageImpl.getAllItemsFromTo(0, 10, 1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(requestDto.getId());
    }
}
