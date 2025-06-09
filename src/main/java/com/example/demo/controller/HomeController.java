package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.example.demo.service.MissionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final MissionService missionService;
    private final PurchaseRewardRepository purchaseRewardRepository;
    private final UserPurchaseRewardRepository userPurchaseRewardRepository; 
    
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        UserDto user = userService.getUserById((int) userId);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        // User info
        model.addAttribute("fullName", user.getFull_name());
        model.addAttribute("spin", user.getSpin());
        model.addAttribute("totalSpent", user.getTotal_spent());

        // Purchase rewards
        List<PurchaseReward> purchaseRewards = purchaseRewardRepository.findAllByOrderByRewardIdAsc();
        model.addAttribute("purchaseRewards", purchaseRewards);

        // FIX: Get actual claimed purchase rewards instead of empty list
        List<UserPurchaseReward> claimedRewards = userPurchaseRewardRepository.findByUserId((int) userId);
        model.addAttribute("claimedRewards", claimedRewards);

        // Missions
        missionService.addMissionsToModel(model, (int) userId);

        return "index";
    }

    @PostMapping("/api/spin")
    @ResponseBody
    public String spinWheel(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return "error";
        }

        try {
            missionService.updateDailyMission((Integer) userId, 2); 
            missionService.updateOnetimeMission((Integer) userId, 3); 
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}
