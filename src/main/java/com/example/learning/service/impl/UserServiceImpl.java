package com.example.learning.service.impl;

import com.example.learning.dto.UserDto;
import com.example.learning.entity.User;
import com.example.learning.repository.UserRepository;
import com.example.learning.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        User saved = userRepo.save(user);

        UserDto response =  new UserDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());

        return response;
    }
}
