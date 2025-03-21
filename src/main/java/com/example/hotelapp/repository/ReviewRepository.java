package com.example.hotelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hotelapp.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query ("SELECT r.hotel.id FROM Review r WHERE r.hotel.id = :hotelId")
    List<Review> findByHotel_HotelId(Long hotelId); // Find reviews by hotel ID

    @Query ("SELECT r FROM Review r WHERE r.tourist.id = :touristId")
    List<Review> findByTourist_TouristId(Long touristId); // Find reviews by experience ID
}