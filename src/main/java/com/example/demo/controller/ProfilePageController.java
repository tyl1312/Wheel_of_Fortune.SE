package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProfilePageController {
    private final UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        if (userId == null) return "redirect:/";

        var user = userRepository.findById((int) userId).orElse(null);
        if (user == null) return "redirect:/";

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String full_name,
                                @RequestParam String gender,
                                HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) return "redirect:/";

        var user = userRepository.findById((int) userId).orElse(null);
        if (user == null) return "redirect:/";

        user.setFull_name(full_name);
        user.setGender(gender);
        userRepository.save(user);

        return "redirect:/profile";
    }
}
