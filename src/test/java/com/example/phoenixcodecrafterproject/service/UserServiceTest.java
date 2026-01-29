package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.dto.request.CreateUserRequest;
import com.example.phoenixcodecrafterproject.dto.response.UserDTO;
import com.example.phoenixcodecrafterproject.exception.DuplicateResourceException;
import com.example.phoenixcodecrafterproject.model.Role;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.UserRepository;
import com.example.phoenixcodecrafterproject.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any()))
                .thenReturn(User.builder()
                        .id(1)
                        .email("test@gmail.com")
                        .role(Role.USER)
                        .build());

        UserDTO result = userService.createUser(
                CreateUserRequest.builder()
                        .email("test@gmail.com")
                        .password("test123")
                        .build()
        );
        assertEquals("test@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@gmail.com")
                .password("test123")
                .build();
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);
        assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(request));
        verify(userRepository).existsByEmail("test@gmail.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFindUserById() {
        User user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .role(Role.USER)
                .build();
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository).findById(1);
    }
}

