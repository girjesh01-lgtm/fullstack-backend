package com.example.learning.service;

import com.example.learning.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getPagedUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long userId, UserDto dto);
    void deleteUser(Long id);
    Page<UserDto> getPagedUsers(Pageable pageable);
}
