package com.example.demo.model;

import lombok.Data;

@Data
public class GitHubUser {
    private Long id;
    private String login;
    private String avatarUrl;
    private String htmlUrl;
    private String type;
}