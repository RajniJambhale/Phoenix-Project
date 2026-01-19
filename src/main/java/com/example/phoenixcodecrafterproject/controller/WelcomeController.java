package com.example.phoenixcodecrafterproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
        @GetMapping("welcome")
        public String getWelcome()
        {
            return "WELCOME - To Spring Boot Application";
        }
}
