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
            return "login"; // chưa đăng nhập -> hiện trang login
        }
        model.addAttribute("username", user);
        return "index"; // đã đăng nhập -> vào trang chính
    }

    @PostMapping("/")
    public String login(@RequestParam String phone_number,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByPhoneNumberAndPassword(phone_number, password).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login"; // đăng nhập sai -> quay lại login với thông báo lỗi
        }

        session.setAttribute("userId", user.getUser_id()); // dùng để gọi API /users/me
        session.setAttribute("user", user.getPhone_number());     // chỉ để hiển thị tên đăng nhập
        return "redirect:/"; // chuyển hướng về /
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // xoá toàn bộ session
        return "redirect:/";
    }
}
