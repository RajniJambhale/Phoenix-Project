package com.example.phoenixcodecrafterproject.repository;
import com.example.phoenixcodecrafterproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


@Repository
    public interface UserRepository extends JpaRepository<User, Serializable> {
        List<User> findByEmail(String email);
}
