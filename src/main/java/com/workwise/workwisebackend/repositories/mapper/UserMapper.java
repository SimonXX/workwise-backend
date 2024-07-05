package com.workwise.workwisebackend.repositories.mapper;

import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.modelDTO.UserDTO;


public class UserMapper {

    public static UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setCvBase64(user.getCv());
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
