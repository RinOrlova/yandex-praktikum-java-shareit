package ru.practicum.shareit.item.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentStorageImpl implements CommentStorage {
    private final CommentMapper commentMapper;
    private final CommentsRepository commentsRepository;

    @Override
    @Transactional
    public CommentDto addComment(CommentDto commentDto) {
        Comment entity = commentMapper.commentDtoToCommentEntity(commentDto);
        Comment savedEntity = commentsRepository.saveAndFlush(entity);
        commentsRepository.refresh(savedEntity);
        return commentMapper.commentEntityToCommentDto(savedEntity);
    }

}
