package com.example.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}