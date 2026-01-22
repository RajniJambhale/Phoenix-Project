package com.example.phoenixcodecrafterproject.service;
import com.example.phoenixcodecrafterproject.dto.request.CreateUserRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateUserRequest;
import com.example.phoenixcodecrafterproject.dto.response.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(CreateUserRequest request);
    UserDTO getUserById(int id);
    UserDTO updateUserById(int id, UpdateUserRequest request);
    List<UserDTO> getAllUser();
    List<UserDTO> getUserByEmail(String email);
    void deleteUserById(int id);

}
