package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User updateUser(User user, Long id);

    void delete(Long id);

    List<User> getUsers();

    User getUserById(Long id);

}
