package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubApiClient client;

    @GetMapping("/users/{username}")
    public User getUser(@PathVariable String username) {
        return client.getUser(username);
    }
}
