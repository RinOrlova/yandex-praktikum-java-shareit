package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertUser2UserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserDto convertUser2UserDto(User user, Long id) {
        return UserDto.builder()
                .id(id)
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User convertUserDto2User(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
