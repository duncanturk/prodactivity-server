package com.twoyang.prodactivity.server.business.users;

import com.twoyang.prodactivity.server.api.User;
import com.twoyang.prodactivity.server.api.UserCreation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllForUser();
    }

    @PostMapping
    public User createUser(@RequestBody UserCreation command) {
        return userService.create(command);
    }
}
