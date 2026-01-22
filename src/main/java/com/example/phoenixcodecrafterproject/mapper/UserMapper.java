package com.example.phoenixcodecrafterproject.mapper;

import com.example.phoenixcodecrafterproject.dto.request.CreateUserRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateUserRequest;
import com.example.phoenixcodecrafterproject.dto.response.UserDTO;
import com.example.phoenixcodecrafterproject.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    // Entity → DTO
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    // Create Request → Entity
    public User toEntity(CreateUserRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    // Update Request → Existing Entity
    public void updateEntity(User user, UpdateUserRequest request) {
        if (request.getName() != null) {
            user.setUsername(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
    }
}
