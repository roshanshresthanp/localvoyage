package com.example.hotelapp.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelapp.entity.Booking;
import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Review;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.BookingExperienceService;
import com.example.hotelapp.service.BookingService;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.ReviewService;
import com.example.hotelapp.service.TouristService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private TouristService touristService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingExperienceService bookingExperienceService;
    

    @GetMapping("/dashboard")
    public String hotelDashboard(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Hotel hotel = (Hotel) session.getAttribute("user");
        model.addAttribute("hotel", hotel);
        return "hotel-dashboard";
    }

    //list of booking from tourist
    @GetMapping("/bookinglistHotel")
    public String BookingListHotel(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        Hotel hotel = (Hotel) session.getAttribute("user");
        model.addAttribute("hotel", hotel);
        
        // Fetch bookings for the hotel
        List<Booking> bookings = bookingService.getBookingsByHotelId(hotel.getId());
        model.addAttribute("bookings", bookings);
        
        return "hotel-booking-list"; // Return the view name
    }

    //list of booked experience
    @GetMapping("/bookingExperiencelistHotel")
    public String BookingExperiencelistHotel(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        Hotel hotel = (Hotel) session.getAttribute("user");
        model.addAttribute("hotel", hotel);
        
        // Fetch bookings for the hotel
        List<BookingExperience> bookingExperiences = bookingExperienceService.getBookingExperienceByHotelId(hotel.getId());
        model.addAttribute("bookingExperiences", bookingExperiences);
        
        return "hotel-booking-experience-list"; // Return the view name
    } 

    ////////////////////////////////////////////
    


    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Hotel hotel = (Hotel) session.getAttribute("user");
        model.addAttribute("hotel", hotel);
        return "hotel-profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "hotel-change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
        @RequestParam String currentPassword,
        @RequestParam String newPassword,
        HttpSession session,
        Model model
    ) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Hotel hotel = (Hotel) session.getAttribute("user");
        if (hotel.getPassword().equals(currentPassword)) {
            hotel.setPassword(newPassword);
            hotelService.updateHotel(hotel);
            model.addAttribute("success", "Password changed successfully!");
        } else {
            model.addAttribute("error", "Current password is incorrect!");
        }
        return "hotel-change-password";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/update")
    public String updateHotelProfile(
        @ModelAttribute Hotel hotel,
        HttpSession session,
        Model model
    ) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        // Retrieve the logged-in hotel from the session
        Hotel loggedInHotel = (Hotel) session.getAttribute("user");
        if (loggedInHotel == null) {
            return "redirect:/login";
        }

        // Update the hotel's profile information
        loggedInHotel.setHotelName(hotel.getHotelName());
        loggedInHotel.setAddress(hotel.getAddress());
        loggedInHotel.setContactNumber(hotel.getContactNumber());

        // Save the updated hotel profile
        hotelService.updateHotel(loggedInHotel);

        // Update the session with the latest hotel data
        session.setAttribute("user", loggedInHotel);

        // Add a success message to the model
        model.addAttribute("success", "Profile updated successfully!");
        return "redirect:/hotel/dashboard";
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        Hotel hotel = (Hotel) session.getAttribute("user");
        model.addAttribute("hotel", hotel);
        return "hotel-edit-profile";
    }

    // tourist ////////////////////////////////////////////////
    // List all tourist

    @GetMapping("/tourists")
    public String listTourist(Model model,HttpSession session) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        List<Tourist> tourists = touristService.getAllTourist();
        model.addAttribute("tourists", tourists);
        return "tourist-list";
    }

    @GetMapping("/add-tourist")
    public String showNewTouristForm(Model model, HttpSession session) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Tourist tourist = new Tourist();
        model.addAttribute("tourist", tourist);
        return "add-tourist";
    }

    @PostMapping("/saveTourist")
    public String saveTourist(@ModelAttribute Tourist tourist,
                           @RequestParam("profilePicture") MultipartFile file, HttpSession session) throws IOException {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
                }
        touristService.saveTourist(tourist, file);
        return "redirect:/hotel/tourists";
    }

    
    @GetMapping("/showTouristForUpdate/{id}")
    public String showTouristForUpdate(@PathVariable(value = "id") long id, Model model) {
        Tourist tourist = touristService.getTouristById(id);
        model.addAttribute("tourist", tourist);
        return "edit-tourist";
    }

    @PostMapping("/updateTourist")
    public String updateTourist(@ModelAttribute Tourist tourist,
                           @RequestParam("profilePicture") MultipartFile file, HttpSession session) throws IOException {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
                }
        touristService.updateTourist(tourist.getId(), tourist, file);
        return "redirect:/hotel/tourists";
    }

    @GetMapping("/deleteTourist/{id}")
    public String deleteTourist(@PathVariable(value = "id") long id) {
        this.touristService.deleteTourist(id);
        return "redirect:/hotel/tourists";
    }

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews/hotel-reviews")
        public String viewMyBookings(Model model,HttpSession session) {

            if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
                return "redirect:/login"; // Redirect to login if not a tourist
            }
            Hotel loggedInHotel = (Hotel) session.getAttribute("user");

            List<Review> reviews = reviewService.getReviewsByHotelId(loggedInHotel.getId());

            model.addAttribute("reviews", reviews);
            return "review-list-hotel";
        }
    
}