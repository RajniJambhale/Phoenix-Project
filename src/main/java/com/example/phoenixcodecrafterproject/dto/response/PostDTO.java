package com.example.phoenixcodecrafterproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

        private long id;
        private String title;
        private String content;

        //  user info
        private int userId;
        private String userName;
}
