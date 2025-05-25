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
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Wrong email or password");
            return "login";
        }

        if (!password.matches(user.getPassword())) {
            model.addAttribute("error", "Wrong email or password");
            return "redirect:/login";
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
                           HttpSession session){

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already in use");
            return "redirect:/register";
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

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object email = session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        model.addAttribute("fullName", session.getAttribute("fullName"));
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
