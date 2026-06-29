package com.fayyaad.transactionapi.controller;
import com.fayyaad.transactionapi.model.User;
import com.fayyaad.transactionapi.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userOps;

    public UserController(UserService userService) {
        this.userOps = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userOps.createUser(user);
    }
}
