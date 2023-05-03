package com.developers.uzchat.security.auth;

public record RegisterRequest(String bio, String email, String name, String username, String password) {
}