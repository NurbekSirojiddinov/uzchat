package com.developers.uzchat.dto;

import com.developers.uzchat.domain.ChannelEntity;
import com.developers.uzchat.domain.Message;

import java.util.List;

public record ChannelDto(Long id, String introVideo, String description, String name, String username,
                         List<UserDto> members,
                         List<Message> messages) {
    public static ChannelDto toPojo(ChannelEntity channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getIntroVideo(),
                channel.getDescription(),
                channel.getName(),
                channel.getUsername(),
                null,
                channel.getMessages());
    }


    public static ChannelDto toPojoWithMembers(ChannelEntity channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getIntroVideo(),
                channel.getDescription(),
                channel.getName(),
                channel.getUsername(),
                channel.getMembers().stream().map(UserDto::fromUser).toList(),
                channel.getMessages());
    }


}
