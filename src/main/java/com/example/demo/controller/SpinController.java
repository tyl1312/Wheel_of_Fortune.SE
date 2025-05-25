package com.example.demo.controller;

import com.example.demo.model.Prize;
import com.example.demo.model.PrizeHistory;
import com.example.demo.model.User;
import com.example.demo.repository.PrizeHistoryRepository;
import com.example.demo.repository.PrizeRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spin")
public class SpinController {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private PrizeHistoryRepository prizeHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Prize spin(@RequestParam("userId") int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RuntimeException("User not found");

        User user = optionalUser.get();

        List<Prize> prizes = prizeRepository.findAll();
        Prize result = selectPrize(prizes);

        // Lưu lịch sử
        PrizeHistory history = new PrizeHistory();
        history.setUser(user);
        history.setPrize(result);
        history.setWonAt(new Timestamp(System.currentTimeMillis()));
        prizeHistoryRepository.save(history);

        return result;
    }

    private Prize selectPrize(List<Prize> prizes) {
        double rand = Math.random();
        double cumulative = 0.0;
        for (Prize p : prizes) {
            cumulative += p.getPrizeProbability(); // Đã sửa đúng tên hàm
            if (rand <= cumulative) return p;
        }
        return prizes.get(prizes.size() - 1); // fallback
    }
}
