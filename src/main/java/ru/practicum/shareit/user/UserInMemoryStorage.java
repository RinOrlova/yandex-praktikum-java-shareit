package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserInMemoryStorage implements UserStorage {

    private final Map<Long, UserDto> userMap = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public UserDto add(UserDto userDto) {
        log.info("Attempt to add user {}", userDto);
        UserDto userWithId = userDto.toBuilder()
                .id(idCounter)
                .build();
        userMap.put(userWithId.getId(), userWithId);
        log.info("User with id={} successfully added", userWithId.getId());
        idCounter++;
        return userWithId;
    }

    @Override
    public UserDto update(UserDto userDto) {
        Long userId = userDto.getId();
        if (userMap.containsKey(userId)) {
            log.info("Attempt to change user with id={}", userId);
            userMap.put(userId, userDto);
            log.info("User with id={} successfully updated", userId);
            return userDto;
        }
        log.warn("User with id={} is not present", userId);
        throw new UserNotFoundException(userId);
    }

    @Override
    public void delete(Long id) {
        if (userMap.containsKey(id)) {
            log.info("Attempt to delete user with id={}", id);
            userMap.remove(id);
            log.info("User with id={} successfully deleted", id);
        }
        log.warn("User with id={} is not present", id);
        throw new UserNotFoundException(id);
    }

    @Override
    public Collection<UserDto> getAll() {
        return userMap.values();
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }
}
