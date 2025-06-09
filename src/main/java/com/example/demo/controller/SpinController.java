//package com.example.demo.controller;
//
//import com.example.demo.model.Prize;
//import com.example.demo.model.PrizeHistory;
//import com.example.demo.model.PrizeHistoryKey;
//import com.example.demo.model.User;
//import com.example.demo.repository.PrizeHistoryRepository;
//import com.example.demo.repository.PrizeRepository;
//import com.example.demo.repository.UserRepository;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/spin")
//public class SpinController {
//
//    private final PrizeRepository prizeRepository;
//    private final UserRepository userRepository;
//    private final PrizeHistoryRepository prizeHistoryRepository;
//
//    @PostMapping("/save-result")
//    public String saveSpinResult(@RequestParam String reward, HttpSession session) {
//        Object sessionUserId = session.getAttribute("userId");
//        if (sessionUserId == null) {
//            return "User not logged in";
//        }
//
//        Integer userId = (Integer) sessionUserId;
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null) {
//            return "User not found";
//        }
//
//        Prize prize = prizeRepository.findByPrizeDescription(reward).orElse(null);
//        if (prize == null) {
//            return "Prize not found: " + reward;
//        }
//
//        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
//
//        PrizeHistoryKey key = new PrizeHistoryKey(user.getUser_id(), prize.getPrizeId(), now);
//        PrizeHistory history = new PrizeHistory();
//        history.setId(key);
//        history.setUser(user);
//        history.setPrize(prize);
//
//        prizeHistoryRepository.save(history);
//
//        return "Saved prize: " + prize.getPrizeName();
//    }
//
//}

package com.example.demo.controller;

import com.example.demo.dto.PrizeHistoryResponse;
import com.example.demo.service.SpinService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spin")
@AllArgsConstructor
public class SpinController {
    
    private final SpinService spinService;
    
    @PostMapping("/save-result")
    public ResponseEntity<Map<String, Object>> saveSpinResult(
            @RequestParam("reward") String reward, 
            HttpSession session) {
        
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            spinService.saveSpinResult((int) userId, reward);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Spin result saved successfully");
            response.put("reward", reward);
            
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<PrizeHistoryResponse>> getSpinHistory(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<PrizeHistoryResponse> history = spinService.getUserSpinHistory((int) userId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
