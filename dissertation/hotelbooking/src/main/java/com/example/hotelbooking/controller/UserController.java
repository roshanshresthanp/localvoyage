package com.example.hotelbooking.controller;




import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.UserService;
import com.example.hotelbooking.session.SessionManager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    
    @Autowired
    private SessionManager sessionManager;
    
    // @GetMapping
    // public String viewHomePage(Model model) {
    //     model.addAttribute("listUsers", userService.getAllUsers());
    //     return "users";
    // }

    // @GetMapping("/showNewUserForm")
    // public String showNewUserForm(Model model) {
    //     User user = new User();
    //     model.addAttribute("user", user);
    //     return "create_user";
    // }

    // @PostMapping("/saveUser")
    // public String saveUser(@ModelAttribute("user") User user,
    //                        @RequestParam("profilePicture") MultipartFile file) throws IOException {
    //     userService.saveOrUpdateUser(user, file);
    //     return "redirect:/users";
    // }
    // @GetMapping("/showFormForUpdate/{id}")
    // public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
    //     User user = userService.getUserById(id);
    //     model.addAttribute("user", user);
    //     return "edit_user";
    // }

    // @GetMapping("/deleteUser/{id}")
    // public String deleteUser(@PathVariable(value = "id") long id) {
    //     this.userService.deleteUser(id);
    //     return "redirect:/users";
    // }

    @GetMapping("/dashboard")
    public String userDashboard(Model model, HttpSession session) {

        User uUser = sessionManager.getCurrentUser(session);
        if (uUser == null || !uUser.getRole().name().equals("USER")) {
            return "redirect:/login";
        }
        model.addAttribute("uUser", uUser);
        
        return "user_dashboard"; // Return the user dashboard view
    }

    // View Profile
    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        // Get the current user from the session
        User currentUser = sessionManager.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        // Add the user to the model for the view
        model.addAttribute("user", currentUser);
        return "profile"; // Return the profile view
    }

    // Show Edit Profile Form
    @GetMapping("/editProfile")
    public String showEditProfileForm(Model model, HttpSession session) {
        // Get the current user from the session
        User currentUser = sessionManager.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        // Add the user to the model for the form
        model.addAttribute("user", currentUser);
        return "edit_user_profile"; // Return the edit profile form
    }

    // Update Profile
    @PostMapping("/updateProfile")
    public String updateProfile(
            @ModelAttribute("user") User updatedUser,
            @RequestParam(value = "profilePicture", required = false) MultipartFile file,
            HttpSession session) throws IOException {

        // Get the current user from the session
        User currentUser = sessionManager.getCurrentUser(session);
        if (currentUser == null) {
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

        return "redirect:/users/profile"; // Redirect to the profile page after updating
    }
    
}