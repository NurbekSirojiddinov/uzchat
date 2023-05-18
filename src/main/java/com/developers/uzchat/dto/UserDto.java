package com.developers.uzchat.dto;

import com.developers.uzchat.domain.User;

public record UserDto(String bio, byte[] profilePhoto, String email, Long id, String name, String username) {
    public static UserDto fromUser(User user) {
        return new UserDto(user.getBio(), user.getProfilePhoto(), user.getEmail(), user.getId(), user.getName(), user.getUsername());
    }
}
