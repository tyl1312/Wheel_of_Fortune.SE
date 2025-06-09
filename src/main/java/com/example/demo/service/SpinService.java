package com.example.demo.service;

import com.example.demo.dto.PrizeHistoryResponse;
import com.example.demo.model.Prize;
import com.example.demo.model.PrizeHistory;
import com.example.demo.model.PrizeHistoryKey;
import com.example.demo.model.User;
import com.example.demo.repository.PrizeHistoryRepository;
import com.example.demo.repository.PrizeRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SpinService {
    
    private final PrizeHistoryRepository prizeHistoryRepository;
    private final PrizeRepository prizeRepository;
    private final UserRepository userRepository;
    
    public void saveSpinResult(int userId, String rewardName) {
        // Find the user
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User not found"));
        
        // Find the prize by description/name
        Prize prize = prizeRepository.findByPrizeDescription(rewardName)
            .orElseThrow(() -> new IllegalStateException("Prize not found: " + rewardName));
        
        // Create prize history record
        PrizeHistory prizeHistory = new PrizeHistory();
        
        // Create composite primary key
        PrizeHistoryKey key = new PrizeHistoryKey();
        key.setUserId(userId);
        key.setPrizeId(prize.getPrizeId());
        key.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        
        prizeHistory.setId(key);
        prizeHistory.setUser(user);
        prizeHistory.setPrize(prize);
        
        // Save to database
        prizeHistoryRepository.save(prizeHistory);
        
        // Handle special prizes that give additional spins
        if ("Spin Again".equals(rewardName)) {
            // "Spin Again" gives one free spin
            user.setSpin(user.getSpin() + 1);
            userRepository.save(user);
        }
        // Note: Other prizes like coupons, vouchers don't add spins
        // They are rewards that users can claim/use outside the game
    }
    
    public List<PrizeHistoryResponse> getUserSpinHistory(int userId) {
        List<PrizeHistory> historyList = prizeHistoryRepository.findByUserIdCustom(userId);
        
        return historyList.stream()
            .map(history -> new PrizeHistoryResponse(
                history.getPrize().getPrizeName(),
                history.getId().getTimestamp()
            ))
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp())) // Most recent first
            .collect(Collectors.toList());
    }
    
    public List<Prize> getAllPrizes() {
        return prizeRepository.findAll();
    }
}