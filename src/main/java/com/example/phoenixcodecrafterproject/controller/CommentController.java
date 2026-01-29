package com.example.phoenixcodecrafterproject.controller;

import com.example.phoenixcodecrafterproject.dto.request.CreateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.response.CommentDTO;
import com.example.phoenixcodecrafterproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @DeleteMapping("/{postIdd}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
