package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    UserDto add(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);

    Collection<UserDto> getAll();

    Optional<UserDto> getById(Long id);
}
