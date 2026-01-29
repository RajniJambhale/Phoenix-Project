package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.dto.request.CreateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.response.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(CreateCommentRequest request);

    List<CommentDTO> getCommentByPostId(Long postId);

    CommentDTO updateComment(Long commentId, UpdateCommentRequest request);

    void deleteComment(Long commentId);
}

