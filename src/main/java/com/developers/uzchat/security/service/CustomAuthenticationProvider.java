package com.developers.uzchat.security.service;

import com.developers.uzchat.domain.User;
import com.developers.uzchat.exception.AuthException;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        LOGGER.info("Authenticating user: {}", username);
        LOGGER.info("Validating credentials for user: {}", username);

        // Call the UserService to retrieve the user with the provided username
        final User user = userRepository.findByEmail(username).orElseThrow(() -> new AuthException("Such user not found"));

        // Check if the user exists and the password matches
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }

        // Create the authentication token with the user's authorities
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
