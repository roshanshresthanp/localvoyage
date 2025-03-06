package com.example.hotelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelapp.entity.ContactForm;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.service.ContactFormService;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.RoomService;


@Controller
public class FrontController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ContactFormService contactFormService;

    @GetMapping("/")
    public String getHomePage() {
        return "front/index";
    }
    
    @GetMapping("/rooms")
    public String getRoomsPage(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms",rooms);
        return "front/rooms";
    }

    @GetMapping("/room")
    public String getRoomPage() {
        
        return "front/room";
    }

    @GetMapping("/amenities")
    public String getAmenitiesPage() {
        return "front/amenities";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "front/contact";
    }

    @PostMapping("/add-contact")
    public String submitContactForm(Model model,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message) {

        // Create a ContactForm object
        ContactForm contactForm = new ContactForm();
        contactForm.setName(name);
        contactForm.setEmail(email);
        contactForm.setSubject(subject);
        contactForm.setMessage(message);

        // Save the contact form data
        contactFormService.saveContactForm(contactForm);
        
        model.addAttribute("successMessage", "Your enquiry has been successfully submitted. We will get back to you soon!");
        return "front/contact";
    }
}
