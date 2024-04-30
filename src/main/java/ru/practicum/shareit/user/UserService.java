package ru.practicum.shareit.user;

import javax.validation.constraints.Positive;
import java.util.List;

public interface UserService {

    User addUser(User user);

    User updateUser(User user, @Positive Long id);

    void delete(Long id);

    List<User> getUsers();

    User getUserById(Long id);

}
