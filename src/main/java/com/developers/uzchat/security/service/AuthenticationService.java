package com.developers.uzchat.security.service;


import com.developers.uzchat.domain.Role;
import com.developers.uzchat.domain.User;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.security.auth.AuthenticationRequest;
import com.developers.uzchat.security.auth.AuthenticationResponse;
import com.developers.uzchat.security.auth.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Assert.hasText(request.email(), "Email cannot be null or blank");
        Assert.hasText(request.password(), "Password cannot be null or blank");

        var user = User.builder()
                .name(request.name())
                .bio(request.bio())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        repository.save(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(request.username(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(), request.getPassword()
//                )
//        );
//        var user = repository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new NoSuchElementException("Such user is not found"));
//        var jwtToken = jwtService.generateToken((UserDetails) user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
}
