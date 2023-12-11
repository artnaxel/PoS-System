package com.demo.PoS.service;

import com.demo.PoS.exceptions.UserNotFoundException;
import com.demo.PoS.model.entity.User;
import com.demo.PoS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public User updateUser(UUID userId, User userDetails) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setUserRole(userDetails.getUserRole());

        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
