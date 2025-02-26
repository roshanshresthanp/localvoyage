package com.example.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelapp.entity.Admin;
import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.AdminService;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.TouristService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TouristService touristService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password,
        HttpSession session,
        Model model
    ) {
        if (adminService.isAdmin(username)) {
            Admin admin = adminService.validateAdminLogin(username, password);
            if (admin != null) {
                session.setAttribute("user", admin);
                session.setAttribute("role", "ADMIN");
                return "redirect:/admin/dashboard";
            }
        } else if (hotelService.isHotel(username)) {
            Hotel hotel = hotelService.validateHotelLogin(username, password);
            if (hotel != null) {
                session.setAttribute("user", hotel);
                session.setAttribute("role", "HOTEL");
                return "redirect:/hotel/dashboard";
            }
        } else if (touristService.isTourist(username)) {
            Tourist tourist = touristService.validateTouristLogin(username, password);
            if (tourist != null) {
                session.setAttribute("user", tourist);
                session.setAttribute("role", "TOURIST");
                return "redirect:/tourist/dashboard";
            }
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}