package com.example.phoenixcodecrafterproject.serviceImpl;

import com.example.phoenixcodecrafterproject.dto.request.CreateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.response.CommentDTO;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.mapper.CommentMapper;
import com.example.phoenixcodecrafterproject.model.Comment;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.repository.CommentRepository;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public CommentDTO addComment(CreateCommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = CommentMapper.toEntity(request, post);
        Comment saved = commentRepository.save(comment);
        return CommentMapper.toCommentDto(saved);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        return commentRepository.findByPostId(postId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .toList();
    }

    @Override
    public CommentDTO updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        CommentMapper.updateEntity(comment, request);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }
}
