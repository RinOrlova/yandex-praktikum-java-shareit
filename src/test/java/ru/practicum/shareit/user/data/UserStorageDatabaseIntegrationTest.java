package ru.practicum.shareit.user.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.mapper.UserMapperImpl;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({UserStorageImpl.class, UserMapperImpl.class})
class UserStorageDatabaseIntegrationTest {

    @Autowired
    private UserStorageImpl userStorageDatabase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testAddUser() {
        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        UserDto savedUser = userStorageDatabase.add(userDto);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Thom Yorke");
        assertThat(savedUser.getEmail()).isEqualTo("thom.yorke@gmail.com");
    }

    @Test
    void testUpdateUser() {
        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        UserDto savedUser = userStorageDatabase.add(userDto);

        savedUser = savedUser.toBuilder().name("Tom York").build();
        UserDto updatedUser = userStorageDatabase.update(savedUser);

        assertThat(updatedUser.getName()).isEqualTo("Tom York");
    }

    @Test
    void testDeleteUser() {
        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        UserDto savedUser = userStorageDatabase.add(userDto);

        userStorageDatabase.delete(savedUser.getId());

        Optional<UserDto> deletedUser = userStorageDatabase.getById(savedUser.getId());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    void testGetAllUsers() {
        UserDto user1 = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        UserDto user2 = UserDto.builder().name("Tom York").email("tom.york@gmail.com").build();

        userStorageDatabase.add(user1);
        userStorageDatabase.add(user2);

        assertThat(userStorageDatabase.getAll()).hasSize(2);
    }

    @Test
    void testGetUserById() {
        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        UserDto savedUser = userStorageDatabase.add(userDto);

        Optional<UserDto> foundUser = userStorageDatabase.getById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.get().getName()).isEqualTo("Thom Yorke");
    }
}
