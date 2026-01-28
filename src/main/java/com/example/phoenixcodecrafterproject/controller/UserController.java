package com.example.phoenixcodecrafterproject.controller;
import com.example.phoenixcodecrafterproject.dto.request.CreateUserRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateUserRequest;
import com.example.phoenixcodecrafterproject.dto.response.ApiResponse;
import com.example.phoenixcodecrafterproject.dto.response.UserDTO;
import com.example.phoenixcodecrafterproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // create new user
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    //get all user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

     // get user by id
    @GetMapping("/user/{id}/get")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id")Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // get user by email
    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // update user by using id
    @PutMapping("user/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUserById(id, request);
        ApiResponse<UserDTO> response = new ApiResponse<>("success", "User updated successfully with id: " + id, updatedUser);
        return ResponseEntity.ok(response);
    }

    //delete user & post by using user id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("user/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        ApiResponse<Void> response = new ApiResponse<>("success", "User deleted successfully with id: " + id, null);
        return ResponseEntity.ok(response);
    }

}

