package com.example.phoenixcodecrafterproject.serviceImpl;
import com.example.phoenixcodecrafterproject.exception.DuplicateResourceException;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.UserRepository;
import com.example.phoenixcodecrafterproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        List<User> user  = userRepository.findAll();
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No user found");
        }
        return user;
    }

    @Override
    public User getUserById(int Id) {
        return userRepository.findById(Id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + Id));

    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        return userRepository.save(user);
    }


    @Override
    public List<User> getUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException(
                    "User not found with email: " + email
            );
        }
        return users;
    }

    @Override
    public User updateUserById(int id, User updateduser) {
        // Find existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        // Update fields
        existingUser.setUsername(updateduser.getUsername());
        existingUser.setEmail(updateduser.getEmail());

        // Save updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

}