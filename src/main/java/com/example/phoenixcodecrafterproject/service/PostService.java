package com.example.phoenixcodecrafterproject.service;
import com.example.phoenixcodecrafterproject.dto.request.CreatePostRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdatePostRequest;
import com.example.phoenixcodecrafterproject.dto.response.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDTO createPost(CreatePostRequest request);
    List<PostDTO> getAllPost();
    PostDTO getPostById(long id);
    PostDTO updatePost(long id, UpdatePostRequest request);
    void deletePost(long id);
    List<PostDTO> getPostsByUser(Long userId);
    List<PostDTO> searchPosts(String keyword);
    List<PostDTO> postsLast7Days();
    Page<PostDTO> getAllPosts(Pageable pageable);

}
