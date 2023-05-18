package com.developers.uzchat.api;

import com.developers.uzchat.security.auth.Authorized;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.developers.uzchat.domain.ChannelEntity;
import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.repository.ChannelRepository;
import com.developers.uzchat.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/channel")
public class ChannelController {
    private final ChannelService channelService;
    private final ChannelRepository channelRepository;

    public ChannelController(ChannelService channelService, ChannelRepository channelRepository) {
        this.channelService = channelService;
        this.channelRepository = channelRepository;
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

    @PostMapping("/upload-audio/{channelId}")
    public ResponseEntity<String> handleAudioUpload(@RequestParam("file") MultipartFile file, @PathVariable Long channelId) {
        try {
            File path = new File("C:\\Users\\nurbe\\OneDrive\\Pictures\\images\\" + file.getOriginalFilename());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();

            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String fileUrl = baseUrl + "\\" + path;



            ChannelEntity entity = channelRepository.findById(channelId).orElseThrow(RuntimeException::new);
            entity.setIntroVideo(path.toString());
            channelRepository.save(entity);
            return ResponseEntity.ok("File is uploaded successfully! " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading video");
        }
}
    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ChannelEntity entity = channelRepository.findById(id)
                .orElseThrow(NoSuchFieldError::new);
        entity.setProfilePhoto(file.getBytes());
        channelRepository.save(entity);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable Long id) {

        ChannelEntity entity = channelRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ByteArrayResource resource = new ByteArrayResource(entity.getProfilePhoto());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""  + "\"")
                .body(resource);
    }

    @GetMapping("/audio/{filename}")
    public ResponseEntity<?> handleAudioDownload(@PathVariable String filename) {
        // Get the directory path from the application properties
        Path path = Paths.get("C:\\Users\\user\\OneDrive\\Pictures\\images\\" + filename + ".png");

        Resource resource = null;
        try {
            URI uri = new URI(path.toUri().toString());
            resource = new UrlResource(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String contentType = "application/octet-stream"; // Default value for binary files

        if (path.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (path.endsWith(".png")) {
            contentType = "image/png";
        }
        assert resource != null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
