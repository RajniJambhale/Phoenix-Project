package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(long id);
    Post updatePost(long id, Post post);
    void deletePost(long id);
}
