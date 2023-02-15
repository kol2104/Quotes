package com.kameleoon.users.service;


import com.kameleoon.users.model.User;
import com.kameleoon.users.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        user.setDateRegistration(new Date());
        return userRepository.save(user);
    }

    public Optional<User> getUserByNameAndPassword(String name, String password) {
        return userRepository.findFirstByNameAndPassword(name, password);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }
}
