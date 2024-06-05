package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.practicum.shareit.user.data.User;
import ru.practicum.shareit.user.model.UserDto;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    ru.practicum.shareit.user.model.User userDto2User(UserDto userDto);

    UserDto user2UserDto(ru.practicum.shareit.user.model.User user);

    UserDto userEntity2UserDto(User user);

    User userDto2UserEntity(UserDto userDto);

    @Named("userToUserId")
    default Long userToUserId(User user) {
        return user == null ? null : user.getId();
    }

    @Named("userDtoToUserId")
    default Long userDtoToUserId(UserDto user) {
        return user == null ? null : user.getId();
    }

    // Helper method to convert userId to UserEntity
    @Named("userIdToUserDto")
    default User userIdToUserDto(Long ownerId) {
        if (ownerId == null) {
            return null;
        }
        User user = new User();
        user.setId(ownerId);
        return user;
    }
}
