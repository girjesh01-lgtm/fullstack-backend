package com.example.learning.service.impl;

import com.example.learning.dto.UserDto;
import com.example.learning.entity.User;
import com.example.learning.exception.EmailAlreadyExistsException;
import com.example.learning.exception.ResourceNotFoundException;
import com.example.learning.repository.UserRepository;
import com.example.learning.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelmapper;

    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper) {

        this.userRepo = userRepo;
        this.modelmapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email alrady exists : " + userDto.getEmail());
        }
        user.setEmail(userDto.getEmail());

        User saved = userRepo.save(user);

        UserDto response =  new UserDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());

        return response;
    }

    @Override
    public List<UserDto> getPagedUsers() {
        List<User> users =  userRepo.findAll();

        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            return userDto;
        }).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email alrady exists : " + dto.getEmail());
        }
        user.setEmail(dto.getEmail());

        User updated = userRepo.save(user);

        UserDto response = new UserDto();
        response.setId(updated.getId());
        response.setName(updated.getName());
        response.setEmail(updated.getEmail());

        return response;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepo.delete(user);
    }

    @Override
    public Page<UserDto> getPagedUsers(Pageable pageable) {

        Page<User> usersPage = userRepo.findAll(pageable);

        /*Page<UserDto> result = usersPage.map(u -> {
            UserDto dto = new UserDto();
            dto.setId(u.getId());
            dto.setName(u.getName());
            dto.setEmail(u.getEmail());
            return dto;
        });
         */

        Page<UserDto> result = usersPage.map(u -> modelmapper.map(u, UserDto.class));

        return result;
    }
}
