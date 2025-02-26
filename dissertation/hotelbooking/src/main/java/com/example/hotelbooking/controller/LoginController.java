package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.session.SessionManager;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login.html template
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            sessionManager.createSession(user, session);
            if (user.getRole().name().equals("ADMIN")) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/users/dashboard";
            }
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = sessionManager.getCurrentUser(session);
        if (user != null) {
            sessionManager.invalidateSession(user.getUsername());
        }
        return "redirect:/login";
    }
}