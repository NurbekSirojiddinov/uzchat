package com.developers.uzchat.api;

import com.developers.uzchat.security.auth.AuthenticationResponse;
import com.developers.uzchat.security.auth.RegisterRequest;
import com.developers.uzchat.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/va/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Registers a new user",
            description = "SECURITY: DISABLED\n\nCurrent API should be called to register a new user")
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }
}
