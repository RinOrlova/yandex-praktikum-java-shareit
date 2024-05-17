package ru.practicum.shareit.item.mapper;


import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.item.data.CommentEntity;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.CommentResponse;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CommentMapper {

    @Mapping(target = "itemEntity", source = "itemId", qualifiedByName = "itemIdToItemEntity")
    @Mapping(target = "userEntity", source = "authorId", qualifiedByName = "userIdToUserDto")
    CommentEntity commentDtoToCommentEntity(CommentDto commentDto);

    @Mapping(target = "itemId", source = "itemEntity", qualifiedByName = "itemEntityToItemId")
    @Mapping(target = "authorId", source = "userEntity", qualifiedByName = "userToUserId")
    @Mapping(target = "authorName", source = "userEntity.name")
    CommentDto commentEntityToCommentDto(CommentEntity savedEntity);

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
    default ItemEntity itemIdToItemEntity(Long itemId) {
        if (itemId == null) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        return itemEntity;
    }

    @Named("itemEntityToItemId")
    default Long itemEntityToItemId(ItemEntity itemEntity) {
        return itemEntity == null
                ? null
                : itemEntity.getId();
    }
}
