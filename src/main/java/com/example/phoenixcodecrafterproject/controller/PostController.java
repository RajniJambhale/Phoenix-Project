package com.example.phoenixcodecrafterproject.controller;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // create post
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@Valid @RequestBody Post post) {

        Post savedPost = postService.createPost(post);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Post created successfully");
        response.put("data", savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // get  all post
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // update post
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

    // delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id){
        postService.deletePost(id);

        Map<String,Object> response =  new HashMap<>();
        response.put("message", "Post deleted successfully with Id " + id);
        return ResponseEntity.ok(response);
    }

    // Find posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getPostsByUser(
            @PathVariable Long userId) {

        List<Post> userPosts = postService.getPostsByUser(userId);

        Map<String, Object> response = new HashMap<>();

        if (userPosts.isEmpty()) {
            response.put("message", "Given user has not created any post yet");
            response.put("posts", userPosts);
            return ResponseEntity.ok(response);
        }

        response.put("message", "Posts fetched successfully");
        response.put("posts", userPosts);

        return ResponseEntity.ok(response);
    }


    //  Search posts by keyword
    // GET /post/search?keyword=spring
    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String keyword) {
        return postService.searchPosts(keyword);
    }

    //  Posts from last 7 days
    @GetMapping("/recent")
    public List<Post> recentPosts() {
        return postService.postsLast7Days();
    }

    //Pagination + sorting
    //post/post?page=0&size=5
    //post?sort=title,asc
    @GetMapping("/post")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

}

