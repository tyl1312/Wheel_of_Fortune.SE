package com.example.demo.service;

import com.example.demo.request.ChangePasswordRequest;
import com.example.demo.request.UpdateUserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(int user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        System.out.println(user);
        User savedUser = userRepository.save(user);
        System.out.println(userMapper.toDto(savedUser));
        return userMapper.toDto(savedUser);
    }

    public UserDto updateUser(int user_id, UpdateUserRequest request) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userMapper.update(request, user);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public void deleteUser(int user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public void changePassword(int user_id, ChangePasswordRequest request) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new RuntimeException("Incorrect old password");
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
