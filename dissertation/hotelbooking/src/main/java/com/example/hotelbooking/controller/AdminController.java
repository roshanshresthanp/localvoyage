package com.example.hotelbooking.controller;

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

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.HotelService;
import com.example.hotelbooking.service.UserService;
import com.example.hotelbooking.session.SessionManager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;


     @Autowired
    private SessionManager sessionManager;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        User adminUser = sessionManager.getCurrentUser(session);
        if (adminUser == null || !adminUser.getRole().name().equals("ADMIN")) {
            return "redirect:/login";
        }
        model.addAttribute("adminUser", adminUser);
        return "admin_dashboard";
    }
     

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userService.findUsersByRole();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("profilePicture") MultipartFile file) throws IOException {
        userService.saveOrUpdateUser(user, file);
        return "redirect:/admin/users";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/editProfile")
    public String showEditProfileForm(Model model, HttpSession session) {
        // Get the current admin user from the session
        User adminUser = sessionManager.getCurrentUser(session);
        if (adminUser == null || !adminUser.getRole().name().equals("ADMIN")) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        // Add the admin user to the model for the form
        model.addAttribute("adminUser", adminUser);
        return "edit_profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @ModelAttribute("adminUser") User updatedUser,
            @RequestParam(value = "profilePicture", required = false) MultipartFile file,
            HttpSession session) throws IOException {

        // Get the current admin user from the session
        User currentUser = sessionManager.getCurrentUser(session);
        if (currentUser == null || !currentUser.getRole().name().equals("ADMIN")) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        // Update the current user's details
        currentUser.setName(updatedUser.getName());
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setDateOfBirth(updatedUser.getDateOfBirth());
        currentUser.setGender(updatedUser.getGender());
        currentUser.setAgeGroup(updatedUser.getAgeGroup());

        // Handle password update (if provided)
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            currentUser.setPassword(updatedUser.getPassword()); // Note: You may want to hash the password here
        }

        // Handle profile picture update (if provided)
        if (file != null && !file.isEmpty()) {
            userService.saveOrUpdateUser(currentUser, file);
        } else {
            userService.saveOrUpdateUser(currentUser, null);
        }

        // Update the session with the updated user details
        sessionManager.createSession(currentUser, session);

        return "redirect:/admin/dashboard"; // Redirect to the dashboard after updating
    }

    // //////////////////////////////////////////////
    // Show all hotels
    @GetMapping("/hotels")
    public String listHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "list_hotels";
    }

    // Show form to create a new hotel
    @GetMapping("/hotels/new")
    public String showHotelRegistrationForm(Model model) {
        model.addAttribute("hotelUser", new User());
        return "hotel_registration";
    }

    // Create a new hotel
    @PostMapping("/hotel/new")
    public String registerHotel(
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

        return "redirect:/admin/hotels"; // Redirect to the hotels list
    }

    // Show form to edit a hotel
    @GetMapping("/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "edit_hotel";
    }

    // Update a hotel
    @PostMapping("/update/{id}")
    public String updateHotel(
            @PathVariable Long id,
            @ModelAttribute("hotel") Hotel updatedHotel) {

        hotelService.updateHotel(id, updatedHotel);
        return "redirect:/admin/hotels"; // Redirect to the hotels list
    }

    // Delete a hotel
    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/admin/hotels"; // Redirect to the hotels list
    }
}