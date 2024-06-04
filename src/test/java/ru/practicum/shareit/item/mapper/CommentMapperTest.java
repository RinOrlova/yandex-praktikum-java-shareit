package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.data.CommentEntity;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentMapperTest {
    @InjectMocks
    private CommentMapper commentMapper = new CommentMapperImpl();
    @Mock
    private UserMapper userMapper;

    @Test
    void testCommentDtoToCommentEntity() {
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .created(LocalDateTime.parse("2024-12-12T00:00:00"))
                .itemId(1L)
                .authorName("author")
                .text("text")
                .build();
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setEmail("email@email.com");
        when(userMapper.userIdToUserDto(any())).thenReturn(userEntity);
        CommentEntity commentEntity = commentMapper.commentDtoToCommentEntity(commentDto);
        assertEquals(LocalDateTime.parse("2024-12-12T00:00:00"), commentEntity.getCreated());
        assertEquals("name", commentEntity.getUserEntity().getName());
        assertEquals("email@email.com", commentEntity.getUserEntity().getEmail());
    }

    @Test
    void testCommentEntityToCommentDto() {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setText("text");
        commentEntity.setCreated(LocalDateTime.parse("2024-12-12T00:00:00"));
        commentEntity.setItemEntity(new ItemEntity());
        commentEntity.setUserEntity(new UserEntity());
        CommentDto commentDto = commentMapper.commentEntityToCommentDto(commentEntity);
        assertEquals(LocalDateTime.parse("2024-12-12T00:00:00"), commentDto.getCreated());
        assertEquals(1L, commentDto.getId());
        assertEquals("text", commentDto.getText());
    }

    @Test
    void testItemIdToItemEntity() {
        assertNull(commentMapper.itemIdToItemEntity(null));
    }

    @Test
    void testItemEntityToItemId() {
        assertNull(commentMapper.itemEntityToItemId(null));
    }

    @Test
    void testMapContext() {
        CommentRequest commentRequest = CommentRequest.builder()
                .text("commentText")
                .build();
        CommentDto commentDto = commentMapper.mapContext(commentRequest, 1L, 2L);
        assertEquals("commentText", commentDto.getText());
        assertEquals(1L, commentDto.getItemId());
        assertEquals(2L, commentDto.getAuthorId());
    }

    @Test
    void testNullBranches() {
        assertNull(commentMapper.commentDtoToCommentEntity(null));
        assertNull(commentMapper.commentEntityToCommentDto(null));
        assertNull(commentMapper.commentDtoToCommentResponse(null));
        assertNull(commentMapper.commentRequestToCommentDto(null, null, null));
    }
}