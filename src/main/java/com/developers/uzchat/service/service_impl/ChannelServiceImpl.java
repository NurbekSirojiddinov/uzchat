package com.developers.uzchat.service.service_impl;

import com.developers.uzchat.domain.ChannelEntity;
import com.developers.uzchat.domain.User;
import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.repository.ChannelRepository;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.service.ChannelService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

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

        final User user = userRepository
                .findById(request.ownerId()).orElseThrow(() -> new NoSuchElementException(String.format("User not found with id [%s]", request.ownerId())));

        final ChannelEntity channel = new ChannelEntity();
        channel.setDescription(request.description());
        channel.setName(request.name());
        channel.setUser(user);
        channel.setIntroVideo("video");


        return ChannelDto.toPojoWithMembers(channelRepository.save(channel));
    }

    @Override
    public ChannelDto findChannel(Long id) {
        return ChannelDto.toPojo(channelRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Channel not found with id [%s]", id))));
    }

    @Override
    public List<ChannelDto> findChannels() {
        return channelRepository.findAll().stream().map(ChannelDto::toPojo).toList();
    }

    @Override
    public void deleteChannel(Long id) {
        Assert.hasText(String.valueOf(id), "Channel id cannot be null or blank");

        channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel not found with id [%s]", id)));
        userRepository.deleteById(id);
    }

    @Override
    public ChannelDto updateChannel(ChannelRequest request, MultipartFile poster) {
        return null;
    }
}
