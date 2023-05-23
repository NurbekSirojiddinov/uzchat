package com.developers.uzchat.dto;

public record ChannelUpdateRequest(Long channelId, String description, String name, String username) {
}
