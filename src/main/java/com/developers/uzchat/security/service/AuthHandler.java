package com.developers.uzchat.security.service;


import com.developers.uzchat.context.UserContext;
import com.developers.uzchat.context.UserPrincipal;
import com.developers.uzchat.security.service.JwtService;
import jakarta.security.auth.message.AuthException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private final JwtService jwtService;

    public AuthHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void authorize(final String jwt) {
        boolean jwtValidated = jwtService.verify(jwt);

        if (jwtValidated) {
            final UserPrincipal user = new UserPrincipal(jwtService.extractUsername(jwt));
            UserContext.setUser(user);

            LOGGER.debug("[{}] user has been authorized", user);
        }   
    }
}

