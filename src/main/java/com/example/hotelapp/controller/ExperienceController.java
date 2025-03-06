package com.example.hotelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.service.ExperienceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hotel/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    // List all experiences
    @GetMapping
    public String listExperiences(Model model,HttpSession session) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Hotel hotel = (Hotel) session.getAttribute("user");
        List<Experience> experiences = experienceService.getExperiencesByHotelId(hotel.getId());
        model.addAttribute("experiences", experiences);
        return "experience-list"; // Return the name of the Thymeleaf template
    }

    // Show add experience form
    @GetMapping("/add")
    public String showAddExperienceForm(Model model, HttpSession session) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        model.addAttribute("experience", new Experience());
        return "add-experience"; // Return the name of the Thymeleaf template
    }

    // Save a new experience
    @PostMapping("/save")
    public String saveExperience(@ModelAttribute Experience experience,HttpSession session, Model model) {

        Hotel hotel = (Hotel) session.getAttribute("user");
        if (hotel == null) {
            
            return "redirect:/login";
        }
        experience.setHotel(hotel);
        experience.setLocal(true);
        experienceService.saveExperience(experience);
        return "redirect:/hotel/experiences"; // Redirect to the experience list
    }

    // Show edit experience form
    @GetMapping("/edit/{id}")
    public String showEditExperienceForm(@PathVariable Long id, Model model, HttpSession session) {

        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Experience experience = experienceService.getExperienceById(id);
        if (experience == null) {
            return "redirect:/hotel/experiences";
        }
        model.addAttribute("experience", experience);
        return "edit-experience"; // Return the name of the Thymeleaf template
    }

    // Update an existing experience
    @PostMapping("/update/{id}")
    public String updateExperience(@PathVariable Long id, @ModelAttribute Experience experience,HttpSession session) {

        Hotel hotel = (Hotel) session.getAttribute("user");
        if (hotel == null) {
            return "redirect:/login";
        }
        experience.setHotel(hotel);
        experienceService.updateExperience(id, experience);
        return "redirect:/hotel/experiences"; // Redirect to the experience list
    }

    // Delete an experience
    @GetMapping("/delete/{id}")
    public String deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return "redirect:/hotel/experiences"; // Redirect to the experience list
    }
}