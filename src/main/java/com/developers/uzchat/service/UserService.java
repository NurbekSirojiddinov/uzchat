package com.developers.uzchat.service;

import com.developers.uzchat.dto.CreateNewUserRequest;
import com.developers.uzchat.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto createNewUser(CreateNewUserRequest request);

    UserDto findUser(Long id);

    List<UserDto> findAllUser();

    void deleteUser(Long id);
}
