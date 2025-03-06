package com.example.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelapp.entity.Booking;
import com.example.hotelapp.service.BookingService;
import com.example.hotelapp.service.HotelService;

@Controller
@RequestMapping("/hotel/bookings")
public class HotelBookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private HotelService hotelService;    

    @PostMapping("/update-status/{bookingId}")
    public String updateBookingStatus(
        @PathVariable Long bookingId,
        @RequestParam String status,
        @RequestParam Long hotelId,
        Model model
    ) {
        Booking booking = bookingService.updateBookingStatus(bookingId, status);
        model.addAttribute("message", "Booking status updated successfully!");
        return "redirect:/hotel/bookings/" + hotelId;
    }

    @PostMapping("/confirm/{bookingId}")
    public String confirmBooking(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId); // Assuming you have this method
        Long hotelId = bookingService.getHotelIdByRoomId(booking.getRoom().getId());

        // Now you can use hotelId for any logic you need
        bookingService.confirmBooking(bookingId);
        model.addAttribute("message", "Booking confirmed successfully!");
        return "redirect:/hotel/bookinglistHotel"; // Redirect to the bookings page
 
    }

    
}