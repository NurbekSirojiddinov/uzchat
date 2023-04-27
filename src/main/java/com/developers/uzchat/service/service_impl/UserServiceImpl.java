package com.developers.uzchat.service.service_impl;

import com.developers.uzchat.domain.User;
import com.developers.uzchat.dto.CreateNewUserRequest;
import com.developers.uzchat.dto.UserDto;
import com.developers.uzchat.repository.UserRepository;
import com.developers.uzchat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createNewUser(CreateNewUserRequest request) {
        Assert.hasText(request.email(), "Email cannot be null or blank");
        Assert.hasText(request.password(), "Password cannot be null or blank");

        final User user = new User();
        user.setBio(request.bio());
        user.setEmail(request.email());
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(request.password());

        return UserDto.fromUser(userRepository.save(user));
    }

    @Override
    public UserDto findUser(Long id) {
        return userRepository
                .findById(id)
                .map(UserDto::fromUser)
                .orElseThrow(() -> new NoSuchElementException(String.format("Such user not found with id [%s] ", id)));
    }

    @Override
    public List<UserDto> findAllUser() {
        return userRepository.findAll().stream().map(UserDto::fromUser).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Such user not found with id [%s] ", id)));
        userRepository.deleteById(id);
    }
}
