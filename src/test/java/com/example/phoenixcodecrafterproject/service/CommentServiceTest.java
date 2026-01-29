package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.dto.request.CreateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdateCommentRequest;
import com.example.phoenixcodecrafterproject.dto.response.CommentDTO;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.model.Comment;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.CommentRepository;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.serviceImpl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void shouldAddCommentSuccessfully() {
        CreateCommentRequest request = CreateCommentRequest.builder()
                .content("Nice post")
                .postId(1L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("Test Post")
                .build();

        Comment savedComment = Comment.builder()
                .id(10L)
                .content("Nice post")
                .post(post)
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(savedComment);

        CommentDTO result = commentService.addComment(request);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Nice post", result.getContent());

        verify(commentRepository).save(Mockito.any(Comment.class));
    }

    @Test
    void shouldThrowExceptionWhenPostNotFound() {
        CreateCommentRequest request = CreateCommentRequest.builder()
                .content("Hello")
                .postId(99L)
                .build();
        when(postRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> commentService.addComment(request));
        verify(commentRepository, never()).save(Mockito.any());
    }

    @Test
    void shouldGetCommentsByPostId() {
        User user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("Test post")
                .user(user)
                .build();

        Comment comment1 = Comment.builder()
                .id(1L)
                .content("First")
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .id(2L)
                .content("Second")
                .post(post)
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        when(commentRepository.findByPostId(1L))
                .thenReturn(List.of(comment1, comment2));

        // when
        List<CommentDTO> result = commentService.getCommentByPostId(1L);

        // then
        assertEquals(2, result.size());
        assertEquals("First", result.get(0).getContent());
        assertEquals("Second", result.get(1).getContent());
    }

    @Test
    void shouldUpdateComment() {
        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .content("Updated comment")
                .build();
        Comment existing = Comment.builder()
                .id(1L)
                .content("Old comment")
                .build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(existing));
        CommentDTO result = commentService.updateComment(1L, request);
        assertEquals("Updated comment", result.getContent());
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = Comment.builder()
                .id(1L)
                .content("To be deleted")
                .build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        commentService.deleteComment(1L);
        verify(commentRepository).delete(comment);
    }
}