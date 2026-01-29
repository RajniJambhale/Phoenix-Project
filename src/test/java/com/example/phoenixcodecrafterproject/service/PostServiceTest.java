package com.example.phoenixcodecrafterproject.service;

import com.example.phoenixcodecrafterproject.dto.request.UpdatePostRequest;
import com.example.phoenixcodecrafterproject.dto.response.PostDTO;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.serviceImpl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void shouldUpdatePost() {
        // --------- Mock Security Context ----------
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@gmail.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        // ------------------------------------------


        // given
        Post post = Post.builder()
                .id(1L)
                .title("old title")
                .content("old content")
                .build();
        UpdatePostRequest req = UpdatePostRequest.builder()
                .title("new title")
                .content("new content")
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));
        when(postRepository.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        PostDTO updated = postService.updatePost(1L, req);
        assertNotNull(updated);
        assertEquals("new title", updated.getTitle());
        assertEquals("new content", updated.getContent());
        verify(postRepository).findById(1L);
        verify(postRepository).save(post);
    }

    @Test
    void shouldThrowExceptionWhenPostNotFound() {
        UpdatePostRequest req = UpdatePostRequest.builder()
                .title("new title")
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> postService.updatePost(1L, req));
    }
}