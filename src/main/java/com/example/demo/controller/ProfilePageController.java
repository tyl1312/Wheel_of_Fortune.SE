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
@RequestMapping("/profile")
public class ProfilePageController {
    private final UserRepository userRepository;

    @GetMapping
    public String profile(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById((Integer) userId).orElse(null);
        if (user == null) return "redirect:/logout";

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@RequestParam String full_name,
                                @RequestParam String gender,
                                @RequestParam(required = false) String email,
                                HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById((Integer) userId).orElse(null);
        if (user == null) return "redirect:/logout";

        user.setFull_name(full_name);
        user.setGender(gender);
        // if (email != null && !email.isBlank()) {
        //     user.setEmail(email);    No email field in User class
        // } 
        
        userRepository.save(user);

        return "redirect:/profile";
    }
}
