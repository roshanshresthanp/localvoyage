package com.example.hotelapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.BookingExperienceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tourist/bookingexperiences")
public class BookingExperienceController {
    
     @Autowired
    private BookingExperienceService bookingExperienceService;

    // tourist booking Experience/////////////////////////

    @GetMapping("/book-experience/{experienceId}")
    public String showBookExperienceForm(@PathVariable Long experienceId, Model model, HttpSession session) {
        // Check if the user is logged in and has the role of TOURIST
        if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
            return "redirect:/login"; // Redirect to login if not a tourist
        }

        // Retrieve the Tourist object from the session
        Tourist tourist = (Tourist) session.getAttribute("user");
        if (tourist == null) {
            return "redirect:/login"; // Redirect to login if tourist is not found
        }

        // Add the touristId to the model
        model.addAttribute("touristId", tourist.getId());

        // Add the roomId to the model
        model.addAttribute("experienceId", experienceId);

        return "book-experience"; // Return the view name
    }

    @PostMapping("/book-experience")
    public String bookExperience(
        @RequestParam Long touristId,
        @RequestParam Long experienceId,
        @RequestParam LocalDate experienceDate,
        Model model
    ) {
        BookingExperience bookingExperience = bookingExperienceService.bookExperience(touristId, experienceId, experienceDate);
        model.addAttribute("bookingExperience", bookingExperience);
        return "booking-experience-confirmation";
    }

    @GetMapping("/my-experience-bookings/{touristId}")
    public String viewMyExperienceBookings(@PathVariable Long touristId, Model model) {
        model.addAttribute("bookingExperiences", bookingExperienceService.getBookingsByTouristId(touristId));
        return "my-experience-bookings";
    }

    
/////////////   Review Experience  ////////////////////////////////////

   

    

}
