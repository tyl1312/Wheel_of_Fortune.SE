package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.model.UserPurchaseReward;
import com.example.demo.repository.UserPurchaseRewardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.model.PurchaseReward;
import com.example.demo.repository.PurchaseRewardRepository;
import com.example.demo.model.Mission;
import com.example.demo.model.MissionType;
import com.example.demo.model.UserMission;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserMissionRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PurchaseRewardRepository purchaseRewardRepository;
    private final UserPurchaseRewardRepository userPurchaseRewardRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Wrong email or password");
            return "login";
        }

        if (!password.equals(user.getPassword())) {
            model.addAttribute("error", "Wrong email or password");
            return "/login";
        }

        // Store user info in session
        session.setAttribute("userId", user.getUser_id());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("fullName", user.getFull_name());
        session.setAttribute("gender", user.getGender());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
            @RequestParam String password,
            @RequestParam String full_name,
            @RequestParam String gender,
            Model model,
            HttpSession session) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already in use");
            return "/register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFull_name(full_name);
        user.setGender(gender);

        userRepository.save(user);

        session.setAttribute("userId", user.getUser_id());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("fullName", user.getFull_name());
        session.setAttribute("gender", user.getGender());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

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

        model.addAttribute("fullName", user.getFull_name());
        model.addAttribute("spin", user.getSpin());
        model.addAttribute("totalSpent", user.getTotal_spent());

        List<PurchaseReward> rewards = purchaseRewardRepository.findAllByOrderByRewardIdAsc();
        model.addAttribute("purchaseRewards", rewards);

        List<UserPurchaseReward> claimedRewards = userPurchaseRewardRepository.findByUserId((int) userId);
        model.addAttribute("claimedRewards", claimedRewards);

        // Add missions to model
        List<Mission> dailyMissions = missionRepository.findByMissionType(MissionType.DAILY);
        List<Mission> oneTimeMissions = missionRepository.findByMissionType(MissionType.ONE_TIME);

        List<UserMission> userMissions = userMissionRepository.findByUserIdAndMissionDate(
                (int) userId, LocalDate.now());

        model.addAttribute("dailyMissions", dailyMissions);
        model.addAttribute("oneTimeMissions", oneTimeMissions);
        model.addAttribute("userMissions", userMissions);
        model.addAttribute("userMissions", userMissions);

        return "index";
    }
}
