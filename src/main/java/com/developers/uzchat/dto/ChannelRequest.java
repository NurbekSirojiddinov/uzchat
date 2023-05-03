package com.developers.uzchat.dto;

public record ChannelRequest(Long ownerId, String description, String name, String username) {
}
