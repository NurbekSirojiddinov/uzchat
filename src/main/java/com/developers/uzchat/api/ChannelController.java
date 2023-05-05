package com.developers.uzchat.api;

import com.developers.uzchat.dto.ChannelDto;
import com.developers.uzchat.dto.ChannelRequest;
import com.developers.uzchat.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    ResponseEntity<ChannelDto> createNewChannel(@RequestPart final ChannelRequest request) {
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

//    @PostMapping("/upload-audio/{channelId}")
//    public ResponseEntity<String> handleAudioUpload(@RequestParam("file") MultipartFile file, @PathVariable Long channelId) {
//        try {
//            File path = new File("C:\\Users\\nurbe\\OneDrive\\Pictures\\images\\" + file.getOriginalFilename());
//            path.createNewFile();
//            FileOutputStream output = new FileOutputStream(path);
//            output.write(file.getBytes());
//            output.close();
//
//            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//            String fileUrl = baseUrl + "\\" + path;
//
//
//
//            ChannelEntity entity = channelRepository.findById(channelId).orElseThrow(RuntimeException::new);
//            entity.setIntroVideo(path.toString());
//            channelRepository.save(entity);
//            return ResponseEntity.ok("File is uploaded successfully! " + fileUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading video");
//        }
//}
//    @PostMapping("/image/{id}")
//    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        ChannelEntity entity = channelRepository.findById(id)
//                .orElseThrow(NoSuchElementException::new);
//        entity.setProfilePhoto(file.getBytes());
//        channelRepository.save(entity);
//        return ResponseEntity.ok("Successful");
//    }

//    @GetMapping("/image/{id}")
//    public ResponseEntity<Resource> downloadVideo(@PathVariable Long id) {
//
//        ChannelEntity entity = channelRepository.findById(id).orElseThrow(NoSuchElementException::new);
//        ByteArrayResource resource = new ByteArrayResource(entity.getProfilePhoto());
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("image/jpeg"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""  + "\"")
//                .body(resource);
//    }

//    @GetMapping("/audio/{filename}")
//    public ResponseEntity<?> handleAudioDownload(@PathVariable String filename) {
//        // Get the directory path from the application properties
//        Path path = Paths.get("C:\\Users\\user\\OneDrive\\Pictures\\images\\" + filename + ".png");
//
//
//        Resource resource = null;
//        try {
//            resource = new UrlResource(path.toUri());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        String contentType = "application/octet-stream"; // Default value for binary files
//
//        if (path.endsWith(".pdf")) {
//            contentType = "application/pdf";
//        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
//            contentType = "image/jpeg";
//        } else if (path.endsWith(".png")) {
//            contentType = "image/png";
//        }
//        assert resource != null;
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }


}
