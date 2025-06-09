package com.example.demo.controller;

import com.example.demo.dto.PrizeHistoryResponse;
import com.example.demo.model.Prize;
import com.example.demo.repository.PrizeRepository;
import com.example.demo.service.SpinService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@AllArgsConstructor
public class SpinController {
    
    private final SpinService spinService;
    private final PrizeRepository prizeRepository;
    
    @GetMapping("/api/prizes")
    public List<Prize> getAllPrizes() {
        return prizeRepository.findAll();
    }
    
    @PostMapping("/api/spin-wheel")
    public Map<String, Object> spinWheel() {
        List<Prize> prizes = prizeRepository.findAll();
        
        if (prizes.isEmpty()) {
            return Map.of("error", "No prizes available");
        }
        
        Prize selectedPrize = selectPrizeByProbability(prizes);
        
        return Map.of(
            "success", true,
            "prizeId", selectedPrize.getPrizeId(),
            "prizeName", selectedPrize.getPrizeName(),
            "probability", selectedPrize.getPrizeProbability()
        );
    }
    
    @PostMapping("/api/save-spin")
    public ResponseEntity<Map<String, String>> saveSpinResult(
            @RequestParam("reward") String reward, 
            HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "User not logged in"));
        }
        
        try {
            spinService.saveSpinResult(userId, reward);
            return ResponseEntity.ok(Map.of("message", "Spin saved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to save spin result"));
        }
    }
    
    @GetMapping("/api/spin-history")
    public ResponseEntity<List<PrizeHistoryResponse>> getSpinHistory(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<PrizeHistoryResponse> history = spinService.getUserSpinHistory(userId);
        return ResponseEntity.ok(history);
    }
    
    private Prize selectPrizeByProbability(List<Prize> prizes) {
        // Calculate total probability
        float totalProbability = 0;
        for (Prize prize : prizes) {
            totalProbability += prize.getPrizeProbability();
        }
        
        float randomValue = new Random().nextFloat() * totalProbability;
        
        // Find winning prize
        float currentProbability = 0;
        for (Prize prize : prizes) {
            currentProbability += prize.getPrizeProbability();
            if (randomValue <= currentProbability) {
                return prize;
            }
        }

        return prizes.get(prizes.size() - 1);
    }
}
