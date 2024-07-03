package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.UserApi;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.modelDTO.UserDTO;
import com.workwise.workwisebackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public List<User> getUserByLastName(String userLastName) {
        return userService.getUserByLastName(userLastName);
    }

    @Override
    public List<User> getUserByName(String userName) {
        return userService.getUserByName(userName);
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        return userService.getUserByEmail(userEmail);
    }
}
