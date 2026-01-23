package com.example.phoenixcodecrafterproject.repository;
import com.example.phoenixcodecrafterproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;


@Repository
    public interface UserRepository extends JpaRepository<User, Serializable> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
