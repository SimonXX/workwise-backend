package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import com.workwise.workwisebackend.repositories.mapper.UserMapper;
import com.workwise.workwisebackend.repositories.modelDTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public List<User> getUserByLastName(String userLastName){
        return userRepository.findByLastName(userLastName);
    }

    public List<User> getUserByName(String userName){
        return userRepository.findByFirstName(userName);
    }

    public UserDTO getUserByEmail(String email){
        Optional<Credential> credential= credentialRepository.findByEmail(email);

        Long credentialId = credential.get().getId();

        Optional<User> user = userRepository.findByCredentials(credentialId);
        UserDTO userDTO = UserMapper.mapUserToUserDTO(user.get());
        return userDTO;

    }
}

