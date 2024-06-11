package ru.practicum.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.client.UserClient;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.USER_PATH)
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userClient.addUser(user);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public User updateUser(@RequestBody User user,
                           @PathVariable @Positive Long id) {
        return userClient.updateUser(user, id);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void deleteUser(@PathVariable @Positive Long id) {
        userClient.delete(id);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userClient.getUsers();
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public User getUserById(@PathVariable @Positive Long id) {
        return userClient.getUserById(id);
    }


}
