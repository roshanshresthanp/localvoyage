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

import com.example.hotelapp.entity.Review;
import com.example.hotelapp.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hotel/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // List all reviews
    @GetMapping
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review-list"; // Return the name of the Thymeleaf template
    }

    // Show add review form
    @GetMapping("/add")
    public String showAddReviewForm(Model model,HttpSession session) {
        if (session.getAttribute("role") == null || !"TOURIST".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        model.addAttribute("review", new Review());
        return "add-review"; // Return the name of the Thymeleaf template
    }

    // Save a new review
    // @PostMapping("/save")
    // public String saveReview(@ModelAttribute Review review) {
    //     reviewService.saveReview(review);
    //     return "redirect:/hotel/reviews"; // Redirect to the review list
    // }

    // Show edit review form
    @GetMapping("/edit/{id}")
    public String showEditReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewService.getAllReviews().stream()
                .filter(r -> r.getReviewId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("review", review);
        return "edit-review"; // Return the name of the Thymeleaf template
    }

    // Update an existing review
    @PostMapping("/update/{id}")
    public String updateReview (@PathVariable Long id, @ModelAttribute Review review) {
        reviewService.updateReview(id, review);
        return "redirect:/hotel/reviews"; // Redirect to the review list
    }

    // Delete a review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/hotel/reviews"; // Redirect to the review list
    }
}