package ru.practicum.shareit.item.data;


import ru.practicum.shareit.item.model.CommentDto;

public interface CommentStorage {

    CommentDto addComment(CommentDto commentDto);

}
