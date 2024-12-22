package com.example.demo.service;

import com.example.demo.model.GitHubUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GitHubService {
    private final WebClient webClient;
    
    public GitHubService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .build();
    }
    
    public Flux<GitHubUser> getUsers(int perPage) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/users")
                    .queryParam("per_page", perPage)
                    .build())
                .retrieve()
                .bodyToFlux(GitHubUser.class);
    }
}