package com.example.demo.controller;

import com.example.demo.request.ChangePasswordRequest;
import com.example.demo.request.UpdateUserRequest;
import com.example.demo.service.UserService;
import com.example.demo.dto.UserDto;
<<<<<<< HEAD
=======
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

<<<<<<< HEAD
import jakarta.servlet.http.HttpSession;
import java.util.List;

=======
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
@RestController
@AllArgsConstructor
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
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto data, UriComponentsBuilder uriBuilder) {
<<<<<<< HEAD
        UserDto created = userService.createUser(data);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(created.getUser_id()).toUri();
        return ResponseEntity.created(uri).body(created);
=======
        // Cần thêm logic save user, không chỉ trả lại data cứng
        // Giả sử bạn đã xử lý save trong service hoặc repo trước
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(data.getUserId()).toUri();
        return ResponseEntity.created(uri).body(data);
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest request) {
<<<<<<< HEAD
        try {
            return ResponseEntity.ok(userService.updateUser(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
=======
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
<<<<<<< HEAD
=======
        if(!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    }
    // ...keep /me endpoints as needed, or move logic to service as well...


    // API lấy và cập nhật user hiện tại (dùng session):
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
<<<<<<< HEAD
        try {
            return ResponseEntity.ok(userService.getUserById((int) userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
=======
        var user = userRepository.findById((int) userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(@RequestBody UpdateUserRequest request, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
<<<<<<< HEAD
        try {
            return ResponseEntity.ok(userService.updateUser((int) userId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
=======
        var user = userRepository.findById((int) userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    }
}
