package ru.practicum.shareit.request.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.data.ItemRequestEntity;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestResponse;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ItemMapper.class})
public interface ItemRequestMapper {
    /* controller-service layer */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemDtos", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "requestor", source = "userId", qualifiedByName = "userIdToUserDto")
    ItemRequestDto mapItemRequest2ItemRequestDto(ItemRequest itemRequest, Long userId);

    default ItemRequestDto mapContext(ItemRequest itemRequest, @Context Long userId) {
        return mapItemRequest2ItemRequestDto(itemRequest, userId);
    }

    @Mapping(target = "items", source = "itemDtos")
    ItemRequestResponse mapItemRequestDto2ItemRequestResponse(ItemRequestDto itemRequestDtoFromStorage);

    /* domain layer */
    @Mapping(target = "itemEntities", ignore = true)
    ItemRequestEntity mapItemRequestDto2ItemRequestEntity(ItemRequestDto requestDto);

    @Mapping(target = "itemDtos", source = "itemEntities")
    ItemRequestDto mapItemRequestEntity2ItemRequestDto(ItemRequestEntity itemRequestEntityFromStorage);
}
