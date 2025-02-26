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

import com.example.hotelapp.entity.Admin;
import com.example.hotelapp.entity.Booking;
import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.entity.ContactForm;
import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.AdminService;
import com.example.hotelapp.service.BookingExperienceService;
import com.example.hotelapp.service.BookingService;
import com.example.hotelapp.service.ContactFormService;
import com.example.hotelapp.service.ExperienceService;
import com.example.hotelapp.service.FileStorageService;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.RoomService;
import com.example.hotelapp.service.TouristService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private HotelService hotelService;

    @Autowired
    private FileStorageService fileStorageService;

    
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        long totalHotelBookings = bookingService.getTotalHotelBookings();
        long totalExperienceBookings = bookingExperienceService.getTotalExperienceBookings();
        long totalTouristsEnrolled = touristService.getTotalTourists();

        // Add counts to the model
        model.addAttribute("totalHotelBookings", totalHotelBookings);
        model.addAttribute("totalExperienceBookings", totalExperienceBookings);
        model.addAttribute("totalTouristsEnrolled", totalTouristsEnrolled);
        model.addAttribute("hotels", hotelService.getAllHotels());

        model.addAttribute("currentPage", "dashboard");
        return "admin-dashboard";
    }

    @GetMapping("/manage-hotel")
    public String adminDashboardHotel(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotels-admin";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "admin-change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
        @RequestParam String currentPassword,
        @RequestParam String newPassword,
        HttpSession session,
        Model model
    ) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Admin admin = (Admin) session.getAttribute("user");
        if (admin.getPassword().equals(currentPassword)) {
            admin.setPassword(newPassword);
            adminService.saveAdmin(admin);
            model.addAttribute("success", "Password changed successfully!");
        } else {
            model.addAttribute("error", "Current password is incorrect!");
        }
        return "admin-change-password";
    }


    @PostMapping("/save")
    public String saveAdmin(@ModelAttribute Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/hotels/add")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "add-hotel";
    }

    @PostMapping("/hotels/save")
    public String saveHotel(@ModelAttribute Hotel hotel) {
        hotel.setRole("HOTEL");
        hotelService.saveHotel(hotel);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/hotels/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "edit-hotel";
    }

    @PostMapping("/hotels/update")
    public String updateHotel(@ModelAttribute Hotel hotel) {
        hotelService.saveHotel(hotel);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/admin/dashboard";
    }

    // profile //////////////
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Admin admin = (Admin) session.getAttribute("user");
        model.addAttribute("admin", admin);
        return "admin-profile";

    }
    @GetMapping("/profile/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        
        Admin admin = (Admin) session.getAttribute("user");
        model.addAttribute("admin", admin);
        return "admin-edit-profile";
    }

        @PostMapping("/profile/update")
        public String updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam("profilePicture") MultipartFile file,
            HttpSession session,
            Model model
        ) {
            if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }

            // Retrieve the Admin object from the session
            Admin admin = (Admin) session.getAttribute("user");
            if (admin == null) {
                return "redirect:/login";
            }

            // Update the admin's profile information
            admin.setFirstName(firstName);
            admin.setLastName(lastName);

            // Handle file upload
            if (!file.isEmpty()) {
                try {
                    // Store the file and get the file name
                    String fileName = fileStorageService.storeFile(file);

                    // Save the file path in the database
                    admin.setProfilePicture(fileName);
                } catch (IOException e) {
                    model.addAttribute("error", "Failed to upload profile picture.");
                    return "admin-edit-profile";
                }
            }

            // Update the admin's profile in the database
            adminService.updateAdminProfile(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getProfilePicture());

            // Update the session with the latest admin data
            session.setAttribute("user", admin);

            // Add a success message to the model
            model.addAttribute("success", "Profile updated successfully!");
            return "redirect:/admin/profile";
        }
    
        // Tourist section ////////////////////////////////////
        
        @Autowired
        private TouristService touristService;
        
        @GetMapping("/tourist")
        public String listTouristAdmin(Model model,HttpSession session) {
            if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
            List<Tourist> tourists = touristService.getAllTourist();
            model.addAttribute("tourists", tourists);
            return "tourist-list-admin";
        }

        @GetMapping("/add-tourist")
        public String showNewTouristFormAdmin(Model model, HttpSession session) {
            if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
            Tourist tourist = new Tourist();
            model.addAttribute("tourist", tourist);
            return "add-tourist-admin";
        }

        @PostMapping("/saveTourist")
        public String saveTourist(@ModelAttribute Tourist tourist,
                            @RequestParam("profilePicture") MultipartFile file, HttpSession session) throws IOException {
            if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/login";
                    }
            touristService.saveTourist(tourist, file);
            return "redirect:/admin/tourist";
        }

        
        @GetMapping("/showTouristForUpdate/{id}")
        public String showTouristForUpdateAdmin(@PathVariable(value = "id") long id, Model model) {
            Tourist tourist = touristService.getTouristById(id);
            model.addAttribute("tourist", tourist);
            return "edit-tourist-admin";
        }

        @PostMapping("/updateTourist")
        public String updateTouristAdmin(@ModelAttribute Tourist tourist,
                            @RequestParam("profilePicture") MultipartFile file, HttpSession session) throws IOException {
             
                    System.out.println("Session role: " + session.getAttribute("role"));
                    if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                        return "redirect:/login";
                    }
            touristService.updateTourist(tourist.getId(), tourist, file);
            return "redirect:/admin/tourist";
        }

        @GetMapping("/deleteTourist/{id}")
        public String deleteTouristAdmin(@PathVariable(value = "id") long id) {
            this.touristService.deleteTourist(id);
            return "redirect:/admin/tourist";
        }

        ////// Room Section ///////////////////////////
        
        
            @Autowired
            private RoomService roomService;

            // List all rooms
            @GetMapping("/rooms")
            public String listAdminRooms(HttpSession session, Model model) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }

                
                List<Room> rooms = roomService.getAllRooms();
                model.addAttribute("rooms", rooms);
                return "room-list-admin";
            }

            // Show add room form
            @GetMapping("/rooms/add-rooms")
            public String showAddAdminRoomForm(HttpSession session, Model model) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                List<Hotel> hotels = hotelService.getAllHotels(); // Fetch the list of hotels
                model.addAttribute("hotels", hotels);
                model.addAttribute("room", new Room());
                return "add-room-admin";
            }

            // Save a new room
            @PostMapping("/rooms/save-room")
            public String savedRoomAdmin(
                @ModelAttribute Room room,
                @RequestParam("profilePicture") MultipartFile file,
                HttpSession session,
                Model model
            ) throws IOException {
            

                // Check session attributes
                Admin admin = (Admin) session.getAttribute("user");
                if (admin == null) {
                    
                    return "redirect:/login";
                }
               
                room.setAvailable(false);
                roomService.saveRoom(room,file);
                model.addAttribute("success", "Room added successfully!");

            
                return "redirect:/admin/rooms";
            }

        

            // Show edit room form
            @GetMapping("/rooms/edit/{id}")
            public String showEditRoomFormAdmin(@PathVariable Long id, HttpSession session, Model model) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }

                Room room = hotelService.getRoomById(id);
                if (room == null) {
                    return "redirect:/admin/rooms";
                }

                model.addAttribute("room", room);
                List<Hotel> hotels = hotelService.getAllHotels();
                model.addAttribute("hotels",hotels);
                return "edit-room-admin";
            }

            @PostMapping("/rooms/update-room")
            public String updatedRoomAdmin(
                @ModelAttribute Room room,
                @RequestParam("profilePicture") MultipartFile file,
                HttpSession session,
                Model model
            ) throws IOException {

                // Check session attributes
                Admin admin = (Admin) session.getAttribute("user");
                if (admin == null) {
                    return "redirect:/login";
                }
                room.setHotel(room.getHotel());
                roomService.updateRoom(room.getId(),room,file);
                model.addAttribute("success", "Room added successfully!");
                return "redirect:/admin/rooms";
            }

            //Delete a room
            @GetMapping("/rooms/delete/{id}")
            public String deleteRoom(@PathVariable Long id, HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }

                hotelService.deleteRoom(id);
                return "redirect:/admin/rooms";
            }

            //////////////////// Hotel Booking /////////
            
            @Autowired
            private BookingService bookingService;

            @GetMapping("/hotel-bookings")
            public String viewMyBookingsAdmin(Model model,HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                List<Booking> bookings = bookingService.getAllBookings();
                model.addAttribute("bookings", bookings);
                return "total-hotel-bookings";
            }

            // Experiences ///////////////////////////

            @Autowired
            private ExperienceService experienceService;
            // List all experiences
            @GetMapping("/experiences")
            public String listExperiencesAdmin(Model model,HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }

                
                List<Experience> experiences = experienceService.getAllExperiences();
                model.addAttribute("experiences", experiences);
                return "experience-list-admin"; // Return the name of the Thymeleaf template
            }

            // Show add experience form
            @GetMapping("/experiences/add")
            public String showAddExperienceFormAdmin(Model model, HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                List<Hotel> hotels = hotelService.getAllHotels(); // Fetch the list of hotels
                model.addAttribute("hotels", hotels);
                model.addAttribute("experience", new Experience());
                return "add-experience-admin"; // Return the name of the Thymeleaf template
            }

            // Save a new experience
            @PostMapping("/experiences/save")
            public String saveExperience(@ModelAttribute Experience experience,HttpSession session, Model model) {

                
                experience.setLocal(true);
                experienceService.saveExperience(experience);
                return "redirect:/admin/experiences"; // Redirect to the experience list
            }

            // Show edit experience form
            @GetMapping("/experiences/edit/{id}")
            public String showEditExperienceForm(@PathVariable Long id, Model model, HttpSession session) {

                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                Experience experience = experienceService.getExperienceById(id);
                if (experience == null) {
                    return "redirect:/admin/experiences";
                }
                model.addAttribute("experience", experience);
                List <Hotel> hotels = hotelService.getAllHotels();
                model.addAttribute("hotels",hotels);
                return "edit-experience-admin"; // Return the name of the Thymeleaf template
            }

            // Update an existing experience
            @PostMapping("/experiences/update/{id}")
            public String updateExperience(@PathVariable Long id, @ModelAttribute Experience experience,HttpSession session) {

                Admin admin = (Admin) session.getAttribute("user");
                if (admin == null) {
                    return "redirect:/login";
                }
                experience.setHotel(experience.getHotel());
                experienceService.updateExperience(id, experience);
                return "redirect:/admin/experiences"; // Redirect to the experience list
            }

            // Delete an experience
            @GetMapping("/experiences/delete/{id}")
            public String deleteExperience(@PathVariable Long id) {
                experienceService.deleteExperience(id);
                return "redirect:/admin/experiences"; // Redirect to the experience list
            }

            ///// Booked Experience ////////////
            @Autowired
            private BookingExperienceService bookingExperienceService;

            @GetMapping("/experience-bookings")
            public String viewMyExperienceBookingsAdmin(Model model,HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                List<BookingExperience> bookingExperiences = bookingExperienceService.getAllBookingExperiences();
                model.addAttribute("bookingExperiences", bookingExperiences);
                return "total-experience-bookings";
            }

            // Enquiry////////////////

            @Autowired
            private ContactFormService contactFormService;
            
            @GetMapping("/contact-forms")
            public String viewEnquiryAdmin(Model model,HttpSession session) {
                if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
                    return "redirect:/login";
                }
                List<ContactForm> contactForms = contactFormService.getAllConatctForm();
                model.addAttribute("contactForms", contactForms);
                return "total-enquiry";
            }

            @GetMapping("/contact-forms/delete/{id}")
            public String deleteEnquiry(@PathVariable Long id) {
                contactFormService.deleteContactForm(id);
                return "redirect:/admin/contact-forms"; // Redirect to the experience list
             }
    }
