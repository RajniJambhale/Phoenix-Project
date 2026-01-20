package com.example.phoenixcodecrafterproject.controller;
import com.example.phoenixcodecrafterproject.dto.request.UserRegistrationDTO;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // create new user
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody  User users)
    {return userService.createUser(users);}

    //get all user
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }

     // get user by id
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id")Integer id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }


    // get user by email
    @GetMapping("/email")
    public ResponseEntity<List<User>> getUserByEmail(@RequestParam String email) {
        List<User> users = userService.getUserByEmail(email);
        return ResponseEntity.ok(users);
    }

    // update user by using id
    @PutMapping("user/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Integer id,
            @RequestBody User userDetails) {

        User updatedUser = userService.updateUserById(id, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User updated successfully with id: " + id);
        response.put("user", updatedUser);
        return ResponseEntity.ok(response);
    }


    //delete post by using id
    @DeleteMapping("user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok("User registered successfully");
    }

}

