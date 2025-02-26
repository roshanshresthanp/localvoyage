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

import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.RoomService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hotel/rooms")
public class RoomController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    // List all rooms
    @GetMapping
    public String listRooms(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Hotel hotel = (Hotel) session.getAttribute("user");
        List<Room> rooms = roomService.getRoomsByHotelId(hotel.getId());

        for (Room room : rooms) {
            System.out.println("Room ID: " + room.getId() + ", Available: " + room.isAvailable());
        }
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    // Show add room form
    @GetMapping("/add")
    public String showAddRoomForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        model.addAttribute("room", new Room());
        return "add-room";
    }

    // Save a new room
    @PostMapping("/save")
    public String savedRoom(
        @ModelAttribute Room room,
        @RequestParam("profilePicture") MultipartFile file,
        HttpSession session,
        Model model
    ) throws IOException {
       

        // Check session attributes
        Hotel hotel = (Hotel) session.getAttribute("user");
        if (hotel == null) {
            
            return "redirect:/login";
        }
        room.setHotel(hotel);
        room.setAvailable(false);
        roomService.saveRoom(room,file);
        model.addAttribute("success", "Room added successfully!");

       
        return "redirect:/hotel/rooms";
    }

    @PostMapping("/update")
    public String updatedRoom(
        @ModelAttribute Room room,
        @RequestParam("profilePicture") MultipartFile file,
        HttpSession session,
        Model model
    ) throws IOException {

        // Check session attributes
        Hotel hotel = (Hotel) session.getAttribute("user");
        if (hotel == null) {
            return "redirect:/login";
        }
        room.setHotel(hotel);
        roomService.updateRoom(room.getId(),room,file);
        model.addAttribute("success", "Room added successfully!");
        return "redirect:/hotel/rooms";
    }

   

    // Show edit room form
    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Room room = hotelService.getRoomById(id);
        if (room == null) {
            return "redirect:/hotel/rooms";
        }

        model.addAttribute("room", room);
        return "edit-room";
    }

    // Delete a room
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("role") == null || !"HOTEL".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        hotelService.deleteRoom(id);
        return "redirect:/hotel/rooms";
    }
}