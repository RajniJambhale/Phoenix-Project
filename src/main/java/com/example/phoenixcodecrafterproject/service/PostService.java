package com.example.phoenixcodecrafterproject.service;
import com.example.phoenixcodecrafterproject.model.Post;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PostService {
    Post createPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(long id);

    Post updatePost(long id, Post post);

    void deletePost(long id);

    List<Post> getPostsByUser(Long userId);

    List<Post> searchPosts(String keyword);

    List<Post> postsLast7Days();

    Page<Post> getAllPosts(int page, int size);

}
