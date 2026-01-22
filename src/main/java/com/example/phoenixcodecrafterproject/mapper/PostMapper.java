package com.example.phoenixcodecrafterproject.mapper;

import com.example.phoenixcodecrafterproject.dto.request.CreatePostRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdatePostRequest;
import com.example.phoenixcodecrafterproject.dto.response.PostDTO;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.model.User;
import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;

@UtilityClass
public class PostMapper {

    public Post toEntity(CreatePostRequest request, User user) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        return post;
    }
    public PostDTO toPostDTO(Post post) {
        if (post == null) return null;

        PostDTO.PostDTOBuilder builder = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent());

        if (post.getUser() != null) {
            builder
                    .userId(post.getUser().getId())
                    .userName(post.getUser().getUsername());
        }

        return builder.build();
    }
    public static void updateEntity(Post post, UpdatePostRequest request) {
        if (request.title() != null) {
            post.setTitle(request.title());
        }
        if (request.content() != null) {
            post.setContent(request.content());
        }
        post.setUpdatedDate(LocalDateTime.now());
    }

}
