package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User getUserById(int Id);
    User createUser(User user);
    List<User> getUserByEmail(String email);
    User updateUserById(int id, User updateduser);
    void deleteUserById(int id);


}
