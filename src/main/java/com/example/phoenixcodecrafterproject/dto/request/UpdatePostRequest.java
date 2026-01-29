package com.example.phoenixcodecrafterproject.dto.request;

import lombok.Builder;

@Builder
public record UpdatePostRequest(
        String title,
        String content
) {
}

