package com.example.phoenixcodecrafterproject.controller;
import com.example.phoenixcodecrafterproject.dto.request.CreatePostRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdatePostRequest;
import com.example.phoenixcodecrafterproject.dto.response.ApiResponse;
import com.example.phoenixcodecrafterproject.dto.response.PostDTO;
import com.example.phoenixcodecrafterproject.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    // create post
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostDTO savedPost = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("success", "Post created successfully", savedPost));
    }

    // get  all post
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }


    // get post by id
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest post) {
        PostDTO updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Post updated successfully with id " + id, updatedPost));
    }

    // delete post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        ApiResponse<Void> response = new ApiResponse<>("success", "Post deleted successfully with Id " + id, null);
        return ResponseEntity.ok(response);
    }

    // Find posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    //  Search posts by keyword /post/search?keyword=spring
    @GetMapping("/search")
    public List<PostDTO> searchPosts(@RequestParam String keyword) {
        return postService.searchPosts(keyword);
    }

    //  Posts from last 7 days
    @GetMapping("/recent")
    public List<PostDTO> recentPosts() {
        return postService.postsLast7Days();
    }

    //Pagination + sorting
    @GetMapping("/pages")
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

}

