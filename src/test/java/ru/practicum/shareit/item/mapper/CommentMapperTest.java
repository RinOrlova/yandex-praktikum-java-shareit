package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.data.Comment;
import ru.practicum.shareit.item.data.Item;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.user.data.User;
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
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");
        when(userMapper.userIdToUserDto(any())).thenReturn(user);
        Comment comment = commentMapper.commentDtoToCommentEntity(commentDto);
        assertEquals(LocalDateTime.parse("2024-12-12T00:00:00"), comment.getCreated());
        assertEquals("name", comment.getUser().getName());
        assertEquals("email@email.com", comment.getUser().getEmail());
    }

    @Test
    void testCommentEntityToCommentDto() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("text");
        comment.setCreated(LocalDateTime.parse("2024-12-12T00:00:00"));
        comment.setItem(new Item());
        comment.setUser(new User());
        CommentDto commentDto = commentMapper.commentEntityToCommentDto(comment);
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