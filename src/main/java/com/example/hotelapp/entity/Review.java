package com.example.hotelapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId; // Primary key

    @ManyToOne
    @JoinColumn(name = "tourist_id", nullable = false)
    private Tourist tourist; // Foreign key linking to the User entity

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel; // Foreign key linking to the Hotel entity

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience; // Foreign key linking to the Experience entity

    @Column(name = "rating", nullable = false)
    private int rating; // Rating (1 to 5 stars)

    @Column(name = "feedback", length = 1000)
    private String feedback; // Detailed feedback from the user

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Timestamp of when the review was created

    
}