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

    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "available", source = "item.available", defaultValue = "false")
    ItemDto item2ItemDto(Item item, Long ownerId);

    default ItemDto mapContext(Item item, @Context Long ownerId) {
        return item2ItemDto(item, ownerId);
    }

    // Mapping from DTO to Entity
    @Mapping(source = "ownerId", target = "userDto", qualifiedByName = "userIdToUserDto")
    ItemEntity itemDtoToItemEntity(ItemDto itemDto);

    // Mapping from Entity to DTO
    @Mapping(source = "userDto", target = "ownerId", qualifiedByName = "userToUserId")
    ItemDto itemEntityToItemDto(ItemEntity itemEntity);

    // Helper method to convert userId to UserEntity
    @Named("userIdToUserDto")
    default UserEntity userIdToUserDto(Long ownerId) {
        if (ownerId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(ownerId);
        return user;
    }

    @Named("requestToRequestId")
    default Long requestToRequestId(ItemRequest itemRequest) {
        return itemRequest == null ? null : itemRequest.getId();
    }

    @Named("itemToItemId")
    default Long itemToItemId(ItemEntity item) {
        return item == null ? null : item.getId();
    }


}
