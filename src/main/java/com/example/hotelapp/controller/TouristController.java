package com.example.hotelapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Review;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.service.ExperienceService;
import com.example.hotelapp.service.FileStorageService;
import com.example.hotelapp.service.HotelService;
import com.example.hotelapp.service.ReviewService;
import com.example.hotelapp.service.RoomService;
import com.example.hotelapp.service.TouristService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private TouristService touristService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ExperienceService experienceService;

    // profile ///////////////////////////////
    
    @GetMapping("/dashboard")
    public String touristDashboard(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Tourist tourist = (Tourist) session.getAttribute("user");
        model.addAttribute("touristId", tourist.getId()); 
        return "tourist-dashboard";
    }

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Tourist tourist = (Tourist) session.getAttribute("user");
        model.addAttribute("touristId", tourist.getId()); 
        model.addAttribute("tourist", tourist);
        return "tourist-profile";

    }
    @GetMapping("/profile/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        
        Tourist tourist = (Tourist) session.getAttribute("user");
        
        model.addAttribute("tourist", tourist);
        return "tourist-edit-profile";
    }

        @PostMapping("/profile/update")
        public String updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String gender,
            @RequestParam String username,
            @RequestParam("profilePicture") MultipartFile file,
            HttpSession session,
            Model model
        ) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }

            // Retrieve the Tourist object from the session
            Tourist tourist = (Tourist) session.getAttribute("user");
            if (tourist == null) {
                return "redirect:/login";
            }

            // Update the tourist's profile information
            tourist.setFirstName(firstName);
            tourist.setLastName(lastName);
            // Convert dateOfBirth from String to LocalDate
            try {
                LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE);
                tourist.setDateOfBirth(dob);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
                return "tourist-edit-profile"; // Return to the edit profile page
            }

            tourist.setGender(gender);
            tourist.setUsername(username);

            // Handle file upload
            if (!file.isEmpty()) {
                try {
                    // Store the file and get the file name
                    String fileName = fileStorageService.storeFile(file);

                    // Save the file path in the database
                    tourist.setTouristPicture(fileName);
                } catch (IOException e) {
                    model.addAttribute("error", "Failed to upload profile picture.");
                    return "tourist-edit-profile";
                }
            }

            // Update the admin's profile in the database
            touristService.updateTouristProfile(tourist.getId(), tourist.getFirstName(), tourist.getLastName(), tourist.getDateOfBirth(), tourist.getGender(), tourist.getUsername(), tourist.getTouristPicture());

            // Update the session with the latest admin data
            session.setAttribute("user", tourist);

            // Add a success message to the model
            model.addAttribute("success", "Profile updated successfully!");
            return "redirect:/tourist/profile";
        }

        // Changing Password //////////////////
         @GetMapping("/change-password")
        public String showChangePasswordForm(HttpSession session, Model model) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
            Tourist tourist = (Tourist) session.getAttribute("user");
            model.addAttribute("touristId", tourist.getId());
            return "tourist-change-password";
        }

        @PostMapping("/change-password")
        public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            HttpSession session,
            Model model
        ) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
            Tourist tourist = (Tourist) session.getAttribute("user");
            if (tourist.getPassword().equals(currentPassword)) {
                tourist.setPassword(newPassword);
                touristService.saveTouristPass(tourist);
                model.addAttribute("success", "Password changed successfully!");
            } else {
                model.addAttribute("error", "Current password is incorrect!");
            }
            return "tourist-change-password";
        }

        @GetMapping("/room")
        public String listRooms(HttpSession session, Model model) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
        
            // Retrieve the Tourist object from the session
            Tourist tourist = (Tourist) session.getAttribute("user");
        
            // Add the Tourist object to the model
            model.addAttribute("tourist", tourist);
        
            // Retrieve the list of rooms
            List<Room> rooms = roomService.getAvailableRooms();
            model.addAttribute("rooms", rooms);

            
            model.addAttribute("touristId", tourist.getId());
        
            return "room-list-tourist"; // Return the view name
        }



        @GetMapping("/experience")
        public String listExperiences(HttpSession session, Model model) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }
        
            // Retrieve the Tourist object from the session
            Tourist tourist = (Tourist) session.getAttribute("user");
            // Add the Tourist object to the model
            model.addAttribute("tourist", tourist);
        
            // Retrieve the list of experiences
            List<Experience> experiences = experienceService.getAllExperiences();
            
            model.addAttribute("touristId", tourist.getId());
            model.addAttribute("experiences", experiences);
        
            return "experience-list-tourist";
        }

        // Review /////////////////////////////////
        @GetMapping("/reviews/reviewExperience/{experienceId}")
        public String showExperienceForm(@PathVariable Long experienceId, Model model, HttpSession session) {
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

            // Add the roomId to the model
            model.addAttribute("experienceId", experienceId);

            return "add-review"; // Return the view name
        }

        @PostMapping("/reviews/add-review")
        public String AddingReview(
            @RequestParam Long touristId,
            @RequestParam Long experienceId,
            @ModelAttribute Review review,
            Model model
        ) {


                    // Fetch the Experience object using the experienceId
            Experience experience = experienceService.getExperienceById(experienceId);
            
            // Check if the experience exists
            if (experience == null) {
                model.addAttribute("error", "Experience not found.");
                return "error-page"; // Redirect to an error page or handle accordingly
            }
            // Set the experience in the review
            review.setExperience(experience);
        
            Tourist tourist = touristService.getTouristById(touristId);
            review.setTourist(tourist);


            Hotel hotel = hotelService.getHotelById(experience.getHotel().getId());
            review.setHotel(hotel);
            reviewService.saveReview(review, touristId, experienceId);
            model.addAttribute("review", review);
            return "my-review-experience";
        }


        @GetMapping("/reviews/my-reviews/{touristId}")
        public String viewMyBookings(@PathVariable Long touristId, Model model,HttpSession session) {

            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login"; // Redirect to login if not a tourist
            }
            model.addAttribute("reviews", reviewService.getReviewsByTouristId(touristId));

            Tourist tourist = (Tourist) session.getAttribute("user");
            model.addAttribute("touristId", tourist.getId());
            return "my-review-experience";
        }

        @GetMapping("/reviews/edit/{id}")
        public String showEditReviewForm(@PathVariable Long id, HttpSession session, Model model) {
            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login";
            }

            Review review = reviewService.getReviewsById(id);
            if (review == null) {
                return "redirect:/tourist/reviews";
            }

            review.setTourist(review.getTourist());
            review.setHotel(review.getHotel());
            review.setExperience(review.getExperience());
            model.addAttribute("review", review);
            Tourist tourist = (Tourist) session.getAttribute("user");
            model.addAttribute("touristId", tourist.getId());
            return "edit-review-tourist";
        }

        //tourist/reviews/update-review
        @PostMapping("/reviews/update-review/{reviewId}")
        public String updateReview (@PathVariable Long reviewId,@ModelAttribute Review review, Model model,HttpSession session) {

            if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
                return "redirect:/login"; // Redirect to login if not a tourist
            }
            Tourist tourist = (Tourist) session.getAttribute("user");
            if (tourist == null) {
                return "redirect:/login"; // Redirect to login if tourist is not found
            }
            // Fetch the existing review from the database
            Review existingReview = reviewService.getReviewsById(reviewId);
            if (existingReview == null) {
                model.addAttribute("error", "Review not found.");
                return "error-page"; // Handle the error accordingly
            }

            // Update the existing review with new values
            existingReview.setRating(review.getRating());
            existingReview.setFeedback(review.getFeedback());
            
            
            existingReview.setExperience(review.getExperience());
            existingReview.setTourist(review.getTourist());

            // Save the updated review
            reviewService.updateReview(reviewId, existingReview);

            return "redirect:/tourist/reviews/my-reviews/" + tourist.getId(); // Redirect to the review list or confirmation page
    }    

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id,HttpSession session,Model model) {
        Tourist tourist = (Tourist) session.getAttribute("user");
            if (tourist == null) {
                return "redirect:/login"; // Redirect to login if tourist is not found
            }
        
        
        model.addAttribute("touristId", tourist.getId());
        reviewService.deleteReview(id);
        return "redirect:/tourist/reviews/my-reviews/" + tourist.getId(); // Redirect to the review list
    }

}
