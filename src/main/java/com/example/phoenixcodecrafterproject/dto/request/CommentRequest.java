package com.example.phoenixcodecrafterproject.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long userId;
    private String content;

}
