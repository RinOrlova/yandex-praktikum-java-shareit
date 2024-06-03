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
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestStorageDatabaseTest {

    @Mock
    private ItemRequestRepository requestRepository;

    @Mock
    private ItemRequestMapper requestMapper;

    @InjectMocks
    private ItemRequestStorageDatabase itemRequestStorageDatabase;

    private ItemRequestDto requestDto;
    private ItemRequestEntity requestEntity;

    @BeforeEach
    void setUp() {
        requestDto = ItemRequestDto.builder()
                .id(1L)
                .description("Test Request")
                .requestor(UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build())
                .build();

        requestEntity = new ItemRequestEntity();
        requestEntity.setId(1L);
        requestEntity.setDescription("Test Request");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Thom Yorke");
        userEntity.setEmail("thom.yorke@gmail.com");

        requestEntity.setRequestor(userEntity);
    }

    @Test
    public void testAdd() {
        when(requestMapper.mapItemRequestDto2ItemRequestEntity(requestDto)).thenReturn(requestEntity);
        when(requestRepository.saveAndFlush(requestEntity)).thenReturn(requestEntity);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        ItemRequestDto result = itemRequestStorageDatabase.add(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(requestDto.getId());
        assertThat(result.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    public void testGetAllItemRequestsByRequestorId() {
        when(requestRepository.getItemRequestEntitiesByRequestor_Id(1L)).thenReturn(Collections.singletonList(requestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        List<ItemRequestDto> result = (List<ItemRequestDto>) itemRequestStorageDatabase.getAllItemRequestsByRequestorId(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(requestDto.getId());
    }

    @Test
    public void testGetById() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(requestEntity));
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        Optional<ItemRequestDto> result = itemRequestStorageDatabase.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(requestDto.getId());
    }

    @Test
    public void testGetAllItemsFromTo() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<ItemRequestEntity> page = new PageImpl<>(Collections.singletonList(requestEntity));
        when(requestRepository.findAllExcludingRequestorId(1L, pageable)).thenReturn(page);
        when(requestMapper.mapItemRequestEntity2ItemRequestDto(requestEntity)).thenReturn(requestDto);

        List<ItemRequestDto> result = (List<ItemRequestDto>) itemRequestStorageDatabase.getAllItemsFromTo(0, 10, 1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(requestDto.getId());
    }
}
