package ru.practicum.shareit.user.service;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.data.UserStorage;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserStorage userStorage;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();
    }

    @Test
    void testAddUser() {
        when(userMapper.user2UserDto(any(User.class))).thenReturn(userDto);
        when(userStorage.add(any(UserDto.class))).thenReturn(userDto);
        when(userMapper.userDto2User(any(UserDto.class))).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertThat(addedUser).isNotNull();
        assertThat(addedUser.getId()).isEqualTo(user.getId());
        assertThat(addedUser.getName()).isEqualTo(user.getName());
        assertThat(addedUser.getEmail()).isEqualTo(user.getEmail());

        verify(userStorage, times(1)).add(userDto);
    }

    @Test
    void testUpdateUser() {
        when(userMapper.user2UserDto(any(User.class))).thenReturn(userDto);
        when(userStorage.update(any(UserDto.class))).thenReturn(userDto);
        when(userStorage.getById(anyLong())).thenReturn(Optional.of(userDto));
        when(userMapper.userDto2User(any(UserDto.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user, user.getId());

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(user.getId());
        assertThat(updatedUser.getName()).isEqualTo(user.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());

        verify(userStorage, times(1)).update(userDto);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userStorage).delete(anyLong());

        userService.delete(user.getId());

        verify(userStorage, times(1)).delete(user.getId());
    }

    @Test
    void testGetAllUsers() {
        when(userStorage.getAll()).thenReturn(Collections.singletonList(userDto));
        when(userMapper.userDto2User(any(UserDto.class))).thenReturn(user);

        List<User> users = userService.getUsers();

        assertThat(users).isNotNull();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getId()).isEqualTo(user.getId());

        verify(userStorage, times(1)).getAll();
    }

    @Test
    void testGetUserById() {
        when(userStorage.getById(anyLong())).thenReturn(Optional.of(userDto));
        when(userMapper.userDto2User(any(UserDto.class))).thenReturn(user);

        User foundUser = userService.getUserById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());

        verify(userStorage, times(1)).getById(user.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userStorage.getById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(user.getId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User by id=" + user.getId() + " not found");

        verify(userStorage, times(1)).getById(user.getId());
    }
}
