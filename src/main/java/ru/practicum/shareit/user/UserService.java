package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User updateUser(User user);

    void delete(Long id);

    List<User> getUsers();

    User getUserById(Long id);

}
