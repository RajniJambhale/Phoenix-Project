package com.example.phoenixcodecrafterproject.repository;
import com.example.phoenixcodecrafterproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PostRepository extends JpaRepository<Post, Serializable> {

}