package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.model.User;
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

        User user = userMapper.userDto2User(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getName()).isEqualTo(userDto.getName());
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void testUser2UserDto() {
        User user = User.builder()
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
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Thom Yorke");
        userEntity.setEmail("thom.yorke@gmail.com");

        UserDto userDto = userMapper.userEntity2UserDto(userEntity);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(userEntity.getId());
        assertThat(userDto.getName()).isEqualTo(userEntity.getName());
        assertThat(userDto.getEmail()).isEqualTo(userEntity.getEmail());
    }

    @Test
    void testUserDto2UserEntity() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        UserEntity userEntity = userMapper.userDto2UserEntity(userDto);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isEqualTo(userDto.getId());
        assertThat(userEntity.getName()).isEqualTo(userDto.getName());
        assertThat(userEntity.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void testUserToUserId() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        Long userId = userMapper.userToUserId(userEntity);

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

        UserEntity userEntity = userMapper.userIdToUserDto(userId);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isEqualTo(userId);
    }

    @Test
    void null_input() {
        assertNull(userMapper.userIdToUserDto(null));
    }
}
