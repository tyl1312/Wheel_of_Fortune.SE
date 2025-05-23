package com.example.demo.controller;

import com.example.demo.dto.ChangePasswordRequest;
import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriBuilder) {
        UserDto createdUser = userService.createUser(userDto);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getUser_id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        try {
            UserDto updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable int id, @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(id, request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Incorrect old password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
    }
}
