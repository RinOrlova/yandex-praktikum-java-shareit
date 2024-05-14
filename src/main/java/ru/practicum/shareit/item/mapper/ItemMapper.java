package ru.practicum.shareit.item.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ItemMapper {
    Item itemDto2Item(ItemDto itemDto);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "available", source = "item.available", defaultValue = "false")
    ItemDto item2ItemDto(Item item, Long userId);

    default ItemDto mapContext(Item item, @Context Long userId) {
        return item2ItemDto(item, userId);
    }

    // Mapping from DTO to Entity
    @Mapping(source = "userId", target = "userDto", qualifiedByName = "userIdToUserDto")
    ItemEntity itemDtoToItemEntity(ItemDto itemDto);

    // Mapping from Entity to DTO
    @Mapping(source = "userDto", target = "userId", qualifiedByName = "userToUserId")
    ItemDto itemEntityToItemDto(ItemEntity itemEntity);

    // Helper method to convert userId to UserEntity
    @Named("userIdToUserDto")
    default UserEntity userIdToUserDto(Long userId) {
        if (userId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }

    @Named("requestToRequestId")
    default Long requestToRequestId(ItemRequest itemRequest) {
        return itemRequest == null ? null : itemRequest.getId();
    }

}
