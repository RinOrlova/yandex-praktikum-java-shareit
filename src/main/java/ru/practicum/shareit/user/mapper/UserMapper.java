package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User userDto2User(UserDto userDto);

    UserDto user2UserDto(User user);

    UserDto userEntity2UserDto(UserEntity userEntity);

    UserEntity userDto2UserEntity(UserDto userDto);

    @Named("userToUserId")
    default Long userToUserId(UserEntity user) {
        return user == null ? null : user.getId();
    }

    @Named("userDtoToUserId")
    default Long userDtoToUserId(UserDto user) {
        return user == null ? null : user.getId();
    }

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
}
