package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MissionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final MissionService missionService;

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
            return "login";
        }

        session.setAttribute("userId", user.getUser_id());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("fullName", user.getFull_name());
        session.setAttribute("gender", user.getGender());

        missionService.handleLoginMissions(user.getUser_id());

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
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFull_name(full_name);
        user.setGender(gender);
        user.setSpin(0);
        user.setTotal_spent(0L);

        User savedUser = userRepository.save(user);

        session.setAttribute("userId", savedUser.getUser_id());
        session.setAttribute("email", savedUser.getEmail());
        session.setAttribute("fullName", savedUser.getFull_name());
        session.setAttribute("gender", savedUser.getGender());

        // For new users, always initialize both missions
        missionService.updateDailyMission(savedUser.getUser_id(), 1);
        missionService.updateOnetimeMission(savedUser.getUser_id(), 7);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}