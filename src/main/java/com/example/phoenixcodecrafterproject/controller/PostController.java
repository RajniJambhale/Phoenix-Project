package com.example.phoenixcodecrafterproject.controller;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Post post) {

        Post savedPost = postService.createPost(post);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Post created successfully");
        response.put("data", savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(
            @PathVariable Long id,
            @RequestBody Post post) {

        Post updatedPost = postService.updatePost(id, post);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post updated successfully with id " + id);
        response.put("post", updatedPost);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id){
        postService.deletePost(id);

        Map<String,Object> response =  new HashMap<>();
        response.put("message", "Post deleted successfully with Id " + id);
        return ResponseEntity.ok(response);
    }
}

