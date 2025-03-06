package com.example.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.service.BookingExperienceService;

@Controller
@RequestMapping("/hotel/bookingExperience")
public class HotelExperienceBookingController {
    
    @Autowired
    private BookingExperienceService bookingExperienceService;

    @PostMapping("/update-status/{bookingExperienceId}")
    public String updateExperienceBookingStatus(
        @PathVariable Long bookingExperienceId,
        @RequestParam String status,
        @RequestParam Long hotelId,
        Model model
    ) {
        BookingExperience bookingExperience = bookingExperienceService.updateBookingStatus(bookingExperienceId, status);
        model.addAttribute("message", "Booking status updated successfully!");
        return "redirect:/hotel/experiences/" + hotelId;
    }

    @PostMapping("/confirm/{bookingExperienceId}")
    public String confirmBookingExperience(@PathVariable Long bookingExperienceId, Model model) {
        BookingExperience bookingExperience = bookingExperienceService.getBookingById(bookingExperienceId); // Assuming you have this method
        Long hotelId = bookingExperienceService.getHotelIdByExperienceId(bookingExperience.getExperience().getId());

        // Now you can use hotelId for any logic you need
        bookingExperienceService.confirmBookingExperience(bookingExperienceId);
        model.addAttribute("message", "Booking confirmed successfully!");
        return "redirect:/hotel/bookingExperiencelistHotel"; // Redirect to the bookings page
 
    }

}
