package com.youtube.uploader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.uploader.models.User;
import com.youtube.uploader.repositories.UserRepository;
import com.youtube.uploader.utils.enums.Provider;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(String firstname, String lastname, String providerId, Provider providerType) {
        User user = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .providerId(providerId)
                .providerType(providerType)
                .build();
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
