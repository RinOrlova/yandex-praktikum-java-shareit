package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDto2User(UserDto userDto);

    UserDto user2UserDto(User user);

    UserDto userEntity2UserDto(UserEntity userEntity);

    UserEntity userDto2UserEntity(UserDto userDto);

    @Named("userToUserId")
    default Long userToUserId(UserEntity user) {
        return user == null ? null : user.getId();
    }
}
