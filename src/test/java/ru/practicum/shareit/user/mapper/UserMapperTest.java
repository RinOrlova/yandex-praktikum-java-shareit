package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.data.User;
import ru.practicum.shareit.user.model.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testUserDto2User() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        ru.practicum.shareit.user.model.User user = userMapper.userDto2User(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getName()).isEqualTo(userDto.getName());
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void testUser2UserDto() {
        ru.practicum.shareit.user.model.User user = ru.practicum.shareit.user.model.User.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        UserDto userDto = userMapper.user2UserDto(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testUserEntity2UserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("Thom Yorke");
        user.setEmail("thom.yorke@gmail.com");

        UserDto userDto = userMapper.userEntity2UserDto(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testUserDto2UserEntity() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        User user = userMapper.userDto2UserEntity(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getName()).isEqualTo(userDto.getName());
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void testUserToUserId() {
        User user = new User();
        user.setId(1L);

        Long userId = userMapper.userToUserId(user);

        assertThat(userId).isNotNull();
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void testUserDtoToUserId() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .build();

        Long userId = userMapper.userDtoToUserId(userDto);

        assertThat(userId).isNotNull();
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void testUserIdToUserDto() {
        Long userId = 1L;

        User user = userMapper.userIdToUserDto(userId);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    void null_input() {
        assertNull(userMapper.userIdToUserDto(null));
    }
}
