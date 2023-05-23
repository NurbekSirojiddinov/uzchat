package com.developers.uzchat.service;

import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.dto.ChannelUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ChannelService {
    ChannelDto createChannel(ChannelRequest request);

    ChannelDto findChannel(Long id);

    List<ChannelDto> findChannels();

    void deleteChannel(Long id);

    ChannelDto updateChannel(ChannelUpdateRequest request);
}
