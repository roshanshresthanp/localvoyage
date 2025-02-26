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

import com.example.hotelapp.entity.Booking;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.BookingService;
import com.example.hotelapp.service.HotelService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tourist/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private HotelService hotelService;

    // tourist booking /////////////////////////

    @GetMapping("/book-room/{roomId}")
    public String showBookRoomForm(@PathVariable Long roomId, Model model, HttpSession session) {
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

        Room room = hotelService.getRoomById(roomId);
        // Add the roomId to the model
        model.addAttribute("room", room);

        return "book-room"; // Return the view name
    }

    @PostMapping("/book-room")
    public String bookRoom(
        @RequestParam Long touristId,
        @RequestParam Long roomId,
        @RequestParam LocalDate checkInDate,
        @RequestParam LocalDate checkOutDate,
        Model model
    ) {
        
        
        Booking booking = bookingService.bookRoom(touristId, roomId, checkInDate, checkOutDate);
        
        model.addAttribute("booking", booking);
        return "booking-confirmation";
    }

    @GetMapping("/my-bookings/{touristId}")
    public String viewMyBookings(@PathVariable Long touristId, Model model) {
        model.addAttribute("bookings", bookingService.getBookingsByTouristId(touristId));
        return "my-bookings";
    }

    
}