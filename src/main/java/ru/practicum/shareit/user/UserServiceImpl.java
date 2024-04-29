package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public User updateUser(User user) {
        UserDto userDto = userConverter.convertUser2UserDto(user);
        UserDto userFromStorage = userStorage.update(userDto);
        return userConverter.convertUserDto2User(userFromStorage);
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
