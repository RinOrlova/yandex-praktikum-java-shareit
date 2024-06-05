package ru.practicum.shareit.item.mapper;


import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.item.data.Comment;
import ru.practicum.shareit.item.data.Item;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.CommentResponse;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = UserMapper.class)
public interface CommentMapper {

    @Mapping(target = "item", source = "itemId", qualifiedByName = "itemIdToItemEntity")
    @Mapping(target = "user", source = "authorId", qualifiedByName = "userIdToUserDto")
    Comment commentDtoToCommentEntity(CommentDto commentDto);

    @Mapping(target = "itemId", source = "item", qualifiedByName = "itemEntityToItemId")
    @Mapping(target = "authorId", source = "user", qualifiedByName = "userToUserId")
    @Mapping(target = "authorName", source = "user.name")
    CommentDto commentEntityToCommentDto(Comment savedEntity);

    CommentResponse commentDtoToCommentResponse(CommentDto commentDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "authorId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorName", ignore = true)
    CommentDto commentRequestToCommentDto(CommentRequest commentRequest, Long itemId, Long userId);

    default CommentDto mapContext(CommentRequest commentRequest, @Context Long itemId, @Context Long userId) {
        return commentRequestToCommentDto(commentRequest, itemId, userId);
    }

    @Named("itemIdToItemEntity")
    default Item itemIdToItemEntity(Long itemId) {
        if (itemId == null) {
            return null;
        }
        Item item = new Item();
        item.setId(itemId);
        return item;
    }

    @Named("itemEntityToItemId")
    default Long itemEntityToItemId(Item item) {
        return item == null
                ? null
                : item.getId();
    }
}
