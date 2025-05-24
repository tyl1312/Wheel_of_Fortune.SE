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
public class HomeController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        User user = userRepository.findByEmailAndPassword(email, password).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }
        session.setAttribute("userId", user.getUser_id());
        session.setAttribute("email", user.getEmail()); // hoặc user.getEmail()

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object email = session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
