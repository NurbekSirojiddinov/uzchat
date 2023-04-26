package com.developers.uzchat.dto;

public record CreateNewUserRequest(String email, String bio, String name, String username, String password) {
}
