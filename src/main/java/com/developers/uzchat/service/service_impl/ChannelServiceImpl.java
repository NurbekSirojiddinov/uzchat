package com.developers.uzchat.service.service_impl;

import com.developers.uzchat.context.UserContext;
import com.developers.uzchat.domain.ChannelEntity;
import com.developers.uzchat.domain.User;
import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.dto.ChannelUpdateRequest;
import com.developers.uzchat.exception.PermissionDeniedException;
import com.developers.uzchat.repository.ChannelRepository;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.security.auth.Authorized;
import com.developers.uzchat.service.ChannelService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChannelDto
    createChannel(ChannelRequest request) {
        Assert.hasText(request.username(), "Channel username cannot be null or blank");
        String username = UserContext.getUsername();

        final User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new NoSuchElementException(String.format("User not found with id [%s]", username)));

        final ChannelEntity channel = new ChannelEntity();
        channel.setDescription(request.description());
        channel.setName(request.name());
        channel.setUser(user);
        channel.setIntroVideo("video");


        return ChannelDto.toPojoWithMembers(channelRepository.save(channel));
    }

    @Override
    public ChannelDto findChannel(Long id) {
        return ChannelDto
                .toPojo(channelRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new NoSuchElementException(String.format("Channel not found with id [%s]", id))));
    }

    @Override
    public List<ChannelDto> findChannels() {
        return channelRepository.findAll().stream().map(ChannelDto::toPojo).toList();
    }

    @Override
    public void deleteChannel(Long id) {
        Assert.hasText(String.valueOf(id), "Channel id cannot be null or blank");

        channelRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel not found with id [%s]", id)));
        userRepository.deleteById(id);
    }

    @Override
    public ChannelDto updateChannel(ChannelUpdateRequest request) {
        Assert.hasText(String.valueOf(request.channelId()), "channelId cannot be null or blank");

        ChannelEntity channel =
                channelRepository
                        .findById(request.channelId())
                        .orElseThrow(
                                () -> new NoSuchElementException(String.format("Such channel nor found with id [%s]", request.channelId())));
        channel.setName(request.name());
        channel.setDescription(request.description());
        channel.setUsername(request.username());
        channel.setLastModifiedDate(Instant.now());

        return ChannelDto.toPojoWithMembers(channel);
    }

    @Authorized
    @Override
    public ChannelDto joinChannel(Long channelId) {
        Assert.hasText(String.valueOf(channelId), "Channel Id cannot be null or blank");

        ChannelEntity channel =
                channelRepository
                        .findById(channelId)
                        .orElseThrow(
                                () -> new NoSuchElementException(String.format("Channel not found with id [%s]", channelId)));
        String username = UserContext.getUsername();
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new NoSuchElementException("Such user doesn't exist!"));

        channel.addMember(user);

        return ChannelDto.toPojo(channelRepository.save(channel));
    }

    @Override
    public ChannelDto removeChannelMember(Long channelId, String username) {
        Assert.hasText(username, "UserId cannot be null or blank");
        Assert.hasText(String.valueOf(channelId), "ChannelId cannot be null or blank");

        ChannelEntity channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel not found with id [%s]", channelId)));
        String loggedOnUsername = UserContext.getUsername();

        if (!channel.getUser().getUsername().equals(loggedOnUsername)) {
            throw new PermissionDeniedException("Only the owner of the channel can block users");
        }
        if (!channel.getMembers().containsKey(username)) {
            throw new NoSuchElementException("Such member doesn't exist");
        }

        channel.getMembers().remove(username);

        return ChannelDto.toPojoWithMembers(channelRepository.save(channel));
    }
}
