package com.example.phoenixcodecrafterproject.serviceImpl;
import com.example.phoenixcodecrafterproject.dto.request.CreateUserRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateUserRequest;
import com.example.phoenixcodecrafterproject.dto.response.UserDTO;
import com.example.phoenixcodecrafterproject.exception.DuplicateResourceException;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.mapper.UserMapper;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.UserRepository;
import com.example.phoenixcodecrafterproject.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        List<User> users  = userRepository.findAll();
        if (users.isEmpty()) {throw new ResourceNotFoundException("No user found");}
        return users.stream().map(UserMapper::toUserDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(int Id) {
        User user =  userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + Id));
        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {throw new DuplicateResourceException("User already exists with email: " + request.getEmail());}
        User user = UserMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return UserMapper.toUserDTO(savedUser);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


    @Override
    public UserDTO updateUserById(int id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserMapper.updateEntity(existingUser, request);
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toUserDTO(updatedUser);
    }

    @Override
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}