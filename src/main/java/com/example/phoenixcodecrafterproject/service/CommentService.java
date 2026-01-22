package com.example.phoenixcodecrafterproject.service;
import com.example.phoenixcodecrafterproject.model.Comment;

public interface CommentService {
    Comment addComment(Long postId, Long userId, String content);
}

