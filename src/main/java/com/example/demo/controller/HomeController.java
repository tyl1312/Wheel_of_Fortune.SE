package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("username", user);
        return "index";
    }

    @PostMapping("/")
    public String login(@RequestParam String phone_number,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByPhoneNumberAndPassword(phone_number, password).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }

        session.setAttribute("userId", user.getUser_id());
        session.setAttribute("user", user.getPhone_number());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userRepository.findById((Integer) userId).orElse(null);
        if (user == null) {
            return "redirect:/logout";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateEmail(@RequestParam String email, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userRepository.findById((Integer) userId).orElse(null);
        if (user != null) {
            user.setEmail(email);
            userRepository.save(user);
        }

        return "redirect:/profile";
    }
}
