package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.UserApi;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.modelDTO.UserDTO;
import com.workwise.workwisebackend.repositories.modelDTO.UserInformationDTO;
import com.workwise.workwisebackend.services.UserService;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

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

    @Override
    public UserDTO updateUser(UserDTO user, String token) {

        String email = jwtUtils.extractJwtToken(token);

        return userService.updateUser(user, email);
    }

    @Override
    public UserInformationDTO getUserInformationByEmail(String userEmail) {

        return userService.getUserCV(userEmail);
    }

    @Override
    public UserInformationDTO getUserInformationById(Long userId) {
        return userService.getUserInformationById(userId);
    }
}
