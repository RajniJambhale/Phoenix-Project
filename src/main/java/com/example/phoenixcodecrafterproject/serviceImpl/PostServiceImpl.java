package com.example.phoenixcodecrafterproject.serviceImpl;
import com.example.phoenixcodecrafterproject.exception.PostNotFoundException;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        Post posts = postRepository.save(post);
        return posts;

    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts  = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new PostNotFoundException("No posts found");
        }
        return posts;
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("Post not found with id: " + id));
    }


    @Override
    public Post updatePost(long id, Post post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("Post not found with id: " + id));
        // Update non-null fields
        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }

        if (post.getContent() != null) {
            existingPost.setContent(post.getContent());
        }


        existingPost.setUpdatedDate(LocalDateTime.now());

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);
    }
}

