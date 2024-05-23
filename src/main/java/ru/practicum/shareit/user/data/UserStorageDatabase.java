package ru.practicum.shareit.user.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserStorageDatabase implements UserStorage {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto userDto) {
        UserEntity userEntity = userMapper.userDto2UserEntity(userDto);
        UserEntity savedEntity = userRepository.saveAndFlush(userEntity);
        return userMapper.userEntity2UserDto(savedEntity);
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserEntity userEntity = userMapper.userDto2UserEntity(userDto);
        UserEntity savedEntity = userRepository.saveAndFlush(userEntity);
        return userMapper.userEntity2UserDto(savedEntity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userEntity2UserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userEntity2UserDto);
    }
}
