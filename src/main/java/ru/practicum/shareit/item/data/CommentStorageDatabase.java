package ru.practicum.shareit.item.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentStorageDatabase implements CommentStorage {
    private final CommentMapper commentMapper;
    private final CommentsRepository commentsRepository;

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        CommentEntity entity = commentMapper.commentDtoToCommentEntity(commentDto);
        CommentEntity savedEntity = commentsRepository.saveAndFlush(entity);
        return commentMapper.commentEntityToCommentDto(savedEntity);
    }

}
