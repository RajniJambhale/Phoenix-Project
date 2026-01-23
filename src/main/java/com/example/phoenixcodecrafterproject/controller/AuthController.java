package com.example.phoenixcodecrafterproject.controller;


import com.example.phoenixcodecrafterproject.dto.request.AuthRequest;
import com.example.phoenixcodecrafterproject.dto.request.UserRegistrationDTO;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.service.CustomUserDetailsService;
import com.example.phoenixcodecrafterproject.service.RegisterService;
import com.example.phoenixcodecrafterproject.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final RegisterService registerService;

@PostMapping("/register")
public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
    registerService.register(dto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("User registered successfully");
}

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }


}
