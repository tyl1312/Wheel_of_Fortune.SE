package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final PrizeRepository prizeRepository;
    private final PrizeHistoryRepository prizeHistoryRepository;

    // Simple admin check - you can enhance this later
    private boolean isAdmin(HttpSession session) {
        Object isAdminObj = session.getAttribute("isAdmin");
        return Boolean.TRUE.equals(isAdminObj);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login?error=access_denied";
        }

        // Simple dashboard statistics
        List<User> allUsers = userRepository.findAll();
        long totalRegularUsers = allUsers.stream()
                .filter(user -> !Boolean.TRUE.equals(user.getIsAdmin()))
                .count(); 
        
        long totalSpins = prizeHistoryRepository.count(); 

        model.addAttribute("totalUsers", totalRegularUsers); 
        model.addAttribute("totalSpins", totalSpins);

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login?error=access_denied";
        }

        // Get all users and filter out admins
        List<User> allUsers = userRepository.findAll();
        List<User> regularUsers = allUsers.stream()
                .filter(user -> !Boolean.TRUE.equals(user.getIsAdmin()))
                .toList();
        
        model.addAttribute("users", regularUsers);

        return "admin/users";
    }

    @GetMapping("/missions")
    public String missions(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login?error=access_denied";
        }

        List<Mission> missions = missionRepository.findAll();
        model.addAttribute("missions", missions);

        return "admin/missions";
    }

    @GetMapping("/prizes")
    public String prizes(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login?error=access_denied";
        }

        List<Prize> prizes = prizeRepository.findAll();
        model.addAttribute("prizes", prizes);

        return "admin/prizes";
    }

    @PostMapping("/prizes/{prizeId}/update-probability")
    @ResponseBody
    public Map<String, Object> updatePrizeProbability(@PathVariable Integer prizeId, // Changed from Long to Integer
                                                     @RequestParam float probability,
                                                     HttpSession session) {
        if (!isAdmin(session)) {
            return Map.of("error", "Access denied");
        }

        try {
            Prize prize = prizeRepository.findById(prizeId).orElseThrow();
            prize.setProbability(probability);
            prizeRepository.save(prize);
            return Map.of("success", true);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}