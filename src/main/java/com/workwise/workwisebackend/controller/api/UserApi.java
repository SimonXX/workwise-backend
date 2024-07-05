package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.modelDTO.UserDTO;
import com.workwise.workwisebackend.support.utils.EntityList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
public interface UserApi {

    @Operation(summary = "Get all the users", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "", produces = "application/json")
    List<User> getAllUsers();

    @Operation(summary = "Get users by Id ", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/id/{userId}", produces = "application/json")
    Optional<User> getUserById(@PathVariable(required = true) Long signID);


    @Operation(summary = "Get users by last name ", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/{userLastName}", produces = "application/json")
    List<User> getUserByLastName(String userLastName);

    @Operation(summary = "Get users by Name ", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/{userName}", produces = "application/json")
    List<User> getUserByName(String userName);

    @Operation(summary = "Get users by Email ", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/email", produces = "application/json")
    UserDTO getUserByEmail(@RequestParam String userEmail);


    @Operation(summary = "Update a user's details", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PutMapping(path = "/change", consumes = "application/json", produces = "application/json")
    UserDTO updateUser(@RequestBody UserDTO user, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

class UserList extends EntityList<User> {}