package com.developers.uzchat.api;

import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.dto.ChannelUpdateRequest;
import com.developers.uzchat.security.auth.Authorized;
import com.developers.uzchat.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/channel")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Authorized
    @Operation(
            summary = "Creates new channel",
            description = "SECURITY: ENABLED\n\nCurrent API should be called to create a new channel")
    @PostMapping("/create")
    ResponseEntity<ChannelDto> createNewChannel(@RequestBody final ChannelRequest request) {
        return ResponseEntity.ok(channelService.createChannel(request));
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

    @Authorized
    @Operation(
            summary = "Deletes channel",
            description = """
                    SECURITY: ENABLED
                                        
                    Deletes a channel corresponding to the id, only channel owner can call this endpoint!""")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteChannel(@PathVariable Long id) {
        channelService.deleteChannel(id);
        return ResponseEntity.ok("Channel has successfully been deleted");
    }

    @Authorized
    @Operation(
            summary = "Updates user's channel",
            description = "SECURITY: ENABLED\n\nUpdates a channel of the user")
    @PatchMapping("/update")
    ResponseEntity<ChannelDto> updateChannel(@RequestBody ChannelUpdateRequest request) {
        return ResponseEntity.ok(channelService.updateChannel(request));
    }

    @Authorized
    @Operation(
            summary = "Request to join channel",
            description = "SECURITY: ENABLED\n\nThis endpoint is used to request to join a channel corresponding to channelId, it can only be used authorized user")
    @PostMapping("/join/{channelId}")
    ResponseEntity<ChannelDto> joinChannel(@PathVariable Long channelId) {
        return ResponseEntity.ok(channelService.joinChannel(channelId));
    }

    @Authorized
    @Operation(
            summary = "Removes channel member",
            description = "SECURITY: ENABLED\n\nThis endpoint is used to remove member of channel. Note this endpoint is only accessible to channel owner")
    @DeleteMapping("/member/{channelId}")
    ResponseEntity<ChannelDto> removeUser(@PathVariable Long channelId, @RequestBody String username) {
        return ResponseEntity.ok(channelService.removeChannelMember(channelId, username));
    }
}
