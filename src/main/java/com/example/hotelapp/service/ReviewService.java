package com.example.hotelapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.entity.Review;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TouristService touristService;

    @Autowired
    private ExperienceService experienceService;

    // Method to get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    // Method to get reviews by hotel ID
    public List<Review> getReviewsByHotelId(Long hotelId) {
        return reviewRepository.findByHotel_HotelId(hotelId);
    }
    //get reviews By Tourist Id
    public List<Review> getReviewsByTouristId(Long touristId) {
        return reviewRepository.findByTourist_TouristId(touristId);
    }

    public Review getReviewsById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
    // Method to save a new review
    public Review saveReview(Review review,Long touristId, Long experienceId) {
        Tourist tourist = touristService.getTouristById(touristId);
        Experience experience = experienceService.getExperienceById(experienceId);

        if (tourist == null || experience == null) {
            throw new IllegalArgumentException("Tourist or Experience not found");
        }

        BookingExperience bookingExperience = new BookingExperience();
        bookingExperience.setTourist(tourist);
        bookingExperience.setExperience(experience);
        review.setCreatedAt(LocalDateTime.now()); // Set the creation timestamp
        return reviewRepository.save(review);
    }

    // Method to update an existing review
    public Review updateReview(Long id, Review review) {
        review.setReviewId(id);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    // Method to delete a review
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}