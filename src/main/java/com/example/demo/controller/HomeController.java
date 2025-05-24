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

<<<<<<< HEAD
    @PostMapping("/")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByEmailAndPassword(email, password).orElse(null);
=======
    @PostMapping("/login")
    public String login(@RequestParam String phoneNumber,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByPhoneNumberAndPassword(phoneNumber, password).orElse(null);
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
        if (user == null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("username", user.getFullName()); // hoặc user.getPhone_number()
        return "redirect:/";
    }

<<<<<<< HEAD
        session.setAttribute("userId", user.getUser_id()); // dùng để gọi API /users/me
        session.setAttribute("user", user.getEmail());     // chỉ để hiển thị tên đăng nhập
        return "redirect:/"; // chuyển hướng về /
=======
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object username = session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "index";
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
