package com.developers.uzchat.dto;

public record CreateNewChannelRequest(Long ownerId, String description, String name, String username) {

}
