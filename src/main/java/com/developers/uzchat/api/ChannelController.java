package com.developers.uzchat.api;

import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/channel")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Operation(
            summary = "Creates new channel",
            description = "SECURITY: ENABLED\n\nCurrent API should be called to create a new channel")
    @PostMapping("/create")
    ResponseEntity<ChannelDto> createNewChannel(@RequestPart final ChannelRequest request, @RequestPart @Nullable MultipartFile poster) {
        return ResponseEntity.ok(channelService.createChannel(request, poster));
    }

    @Operation(
            summary = "Find channel",
            description = "SECURITY: ENABLED\n\nReturns a channel corresponding to the id")
    @GetMapping("/{id}")
    ResponseEntity<ChannelDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.findChannel(id));
    }

    @Operation(
            summary = "Find all channels",
            description = "SECURITY: DISABLED\n\nReturns  List of channels")
    @GetMapping("/all")
    ResponseEntity<List<ChannelDto>> findAllChannel() {
        return ResponseEntity.ok(channelService.findChannels());
    }

    @Operation(
            summary = "Deletes channels",
            description = """
                    SECURITY: ENABLED
                                        
                    Deletes a channel corresponding to the id, only channel owner can call this endpoint!""")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteChannel(@PathVariable Long id) {
        channelService.deleteChannel(id);
        return ResponseEntity.ok("Channel has successfully been deleted");
    }


}
