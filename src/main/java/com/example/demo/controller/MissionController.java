package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MissionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;
    private final UserRepository userRepository; 

    @PostMapping("/claim")
    public ResponseEntity<Map<String, Object>> claimMission(
            @RequestParam Integer missionId,
            @RequestParam String missionType,
            HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Not authenticated"));
        }

        try {
            int spinReward = missionService.claimMission((Integer) userId, missionId, missionType);
            

            User user = userRepository.findById((Integer) userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "spinReward", user.getSpin(),  
                "rewardAmount", spinReward    
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/claim-all")
    public ResponseEntity<Map<String, Object>> claimAllMissions(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Not authenticated"));
        }

        try {
            int totalSpinsReward = missionService.claimAllCompletedMissions((Integer) userId);
            
            User user = userRepository.findById((Integer) userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "totalSpins", user.getSpin(),     
                "rewardAmount", totalSpinsReward  
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
}