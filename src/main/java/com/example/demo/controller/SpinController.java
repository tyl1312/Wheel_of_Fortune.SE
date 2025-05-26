package com.example.demo.controller;

import com.example.demo.model.Prize;
import com.example.demo.model.PrizeHistory;
import com.example.demo.model.PrizeHistoryKey;
import com.example.demo.model.User;
import com.example.demo.repository.PrizeHistoryRepository;
import com.example.demo.repository.PrizeRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spin")
public class SpinController {

    private final PrizeRepository prizeRepository;
    private final UserRepository userRepository;
    private final PrizeHistoryRepository prizeHistoryRepository;

    @PostMapping("/save-result")
    public String saveSpinResult(@RequestParam String reward, HttpSession session) {
        Object sessionUserId = session.getAttribute("userId");
        if (sessionUserId == null) {
            return "User not logged in";
        }

        Integer userId = (Integer) sessionUserId;
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found";
        }

        Prize prize = prizeRepository.findByPrizeDescription(reward).orElse(null);
        if (prize == null) {
            return "Prize not found: " + reward;
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        PrizeHistoryKey key = new PrizeHistoryKey(user.getUser_id(), prize.getPrizeId(), now);
        PrizeHistory history = new PrizeHistory();
        history.setId(key);
        history.setUser(user);
        history.setPrize(prize);

        prizeHistoryRepository.save(history);

        return "Saved prize: " + prize.getPrizeName();
    }

}
