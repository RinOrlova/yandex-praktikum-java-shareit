package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ApiPathConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(ApiPathConstants.USER_PATH)
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PatchMapping(ApiPathConstants.BY_ID_PATH)
    public User updateUser(@RequestBody User user,
                           @PathVariable @Positive Long id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping(ApiPathConstants.BY_ID_PATH)
    public void deleteUser(@PathVariable @Positive Long id) {
        userService.delete(id);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(ApiPathConstants.BY_ID_PATH)
    public User getUserById(@PathVariable @Positive Long id) {
        return userService.getUserById(id);
    }


}
