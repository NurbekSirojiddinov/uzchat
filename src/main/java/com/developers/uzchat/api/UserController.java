package com.developers.uzchat.api;

import com.developers.uzchat.domain.User;
import com.developers.uzchat.dto.CreateNewUserRequest;
import com.developers.uzchat.dto.UserDto;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.security.auth.Authorized;
import com.developers.uzchat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Creates new user",
            description = "SECURITY: ENABLED\n\nCurrent API should be called to create a new user")
    @PostMapping("/create")
    ResponseEntity<UserDto> createNewUser(@RequestBody final CreateNewUserRequest request) {
        return ResponseEntity.ok(userService.createNewUser(request));
    }

    @Operation(
            summary = "Get user",
            description = "SECURITY: ENABLED\n\nCurrent API should be called to retrieve the user")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Authorized
    @Operation(
            summary = "Get all users",
            description = "SECURITY: DISABLED\n\nCurrent API should be called to retrieve the user")
    @GetMapping()
    List<UserDto> findAllUser() {
        return userService.findAllUser();
    }

    @Operation(
            summary = "Delete user",
            description = "SECURITY: ENABLED\n\nCurrent API should be called to retrieve the user")
    @DeleteMapping("/{id}")
    ResponseEntity<?> findAllUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("User has successfully been deleted!");
    }

    @PostMapping("/{id}/profilePicture")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
            user.setProfilePhoto(file.getBytes());
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
