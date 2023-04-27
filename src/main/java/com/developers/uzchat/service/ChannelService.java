package com.developers.uzchat.service;

import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.CreateNewChannelRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChannelService {
    ChannelDto createChannel(CreateNewChannelRequest request);

    ChannelDto findChannel(Long id);

    List<ChannelDto> findChannels();

    void deleteChannel(Long id);
}
