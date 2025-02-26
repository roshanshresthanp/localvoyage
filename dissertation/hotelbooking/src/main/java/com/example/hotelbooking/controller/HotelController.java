package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.UserService;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private UserService userService;


    // Show form to create a new hotel user
    @GetMapping("/register")
    public String showHotelRegistrationForm(Model model) {
        model.addAttribute("hotelUser", new User());
        return "hotel_registration";
    }

    // Create a new hotel user
    @PostMapping("/register")
    public String registerHotelUser(
            @ModelAttribute("hotelUser") User hotelUser,
            @RequestParam String hotelName,
            @RequestParam String location) {

        userService.createHotelUser(
                hotelUser.getName(),
                hotelUser.getUsername(),
                hotelUser.getPassword(),
                hotelName,
                location
        );

        return "redirect:/login"; // Redirect to login page
    }
}