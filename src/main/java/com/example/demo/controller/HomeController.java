package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("username", user);
        return "index";
    }
    @PostMapping("/")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        if (username != null && password != null) {
            session.setAttribute("user", username);
        }
        return "redirect:/"; // Reload "/" and let GET decide what to show
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
