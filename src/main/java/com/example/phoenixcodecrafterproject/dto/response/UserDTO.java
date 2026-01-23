package com.example.phoenixcodecrafterproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UserDTO {

        private int id;
        private String name;
        private String email;
        private String role;
    }
