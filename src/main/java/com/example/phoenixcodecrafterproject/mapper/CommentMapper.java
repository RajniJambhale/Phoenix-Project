package com.example.phoenixcodecrafterproject.mapper;

import com.example.phoenixcodecrafterproject.dto.request.CreateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.response.CommentDTO;
import com.example.phoenixcodecrafterproject.model.Comment;
import com.example.phoenixcodecrafterproject.model.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request, Post post) {
        return Comment.builder()
                .content(request.getContent())
                .post(post)
                .build();
    }

    public CommentDTO toCommentDto(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .build();
    }

    public static void updateEntity(Comment comment, UpdateCommentRequest request) {
        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }
    }
}