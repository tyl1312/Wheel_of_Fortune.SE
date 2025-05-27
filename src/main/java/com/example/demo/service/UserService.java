package com.example.demo.service;

import com.example.demo.request.ChangePasswordRequest;
import com.example.demo.request.UpdateUserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.PurchaseReward;
import com.example.demo.model.User;
import com.example.demo.model.UserPurchaseReward;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PurchaseRewardRepository;
import com.example.demo.repository.UserPurchaseRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PurchaseRewardRepository purchaseRewardRepository;
    private final UserPurchaseRewardRepository userPurchaseRewardRepository;

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

    @Transactional
    public UserDto claimPurchaseReward(int userId, int rewardId) {
        if (userPurchaseRewardRepository.findByUserIdAndRewardId(userId, rewardId).isPresent()) {
            throw new IllegalStateException("Reward already claimed");
        }
        PurchaseReward reward = purchaseRewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserPurchaseReward claim = new UserPurchaseReward();
        claim.setUserId(userId);
        claim.setRewardId(rewardId);
        claim.setClaimedAt(LocalDateTime.now());
        userPurchaseRewardRepository.save(claim);

        user.setSpin(user.getSpin() + reward.getSpin());
        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}
