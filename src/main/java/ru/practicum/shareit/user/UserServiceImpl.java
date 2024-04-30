package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserConverter userConverter;

    @Override
    public User addUser(User user) {
        UserDto userDto = userConverter.convertUser2UserDto(user);
        UserDto userFromStorage = userStorage.add(userDto);
        return userConverter.convertUserDto2User(userFromStorage);
    }


    @Override
    public User updateUser(User user, @Positive Long id) {
        User userToStore = recreateUser(user, id);
        UserDto userDto = userConverter.convertUser2UserDto(userToStore);
        UserDto userFromStorage = userStorage.update(userDto);
        return userConverter.convertUserDto2User(userFromStorage);
    }

    private User recreateUser(User user, Long id) {
        User userById = getUserById(id);
        User.UserBuilder userByIdBuilder = userById.toBuilder();
        if (user.getEmail() != null) {
            userByIdBuilder.email(user.getEmail());
        }
        if (user.getName() != null) {
            userByIdBuilder.name(user.getName());
        }
        return userByIdBuilder.build();
    }

    @Override
    public void delete(Long id) {
        userStorage.delete(id);
    }

    @Override
    public List<User> getUsers() {
        Collection<UserDto> usersFromStorage = userStorage.getAll();
        return usersFromStorage.stream()
                .map(userConverter::convertUserDto2User)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        UserDto userFromStorage = userStorage.getById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userConverter.convertUserDto2User(userFromStorage);
    }
}
