package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ItemMapper.class})
public interface ItemRequestMapper {
}
