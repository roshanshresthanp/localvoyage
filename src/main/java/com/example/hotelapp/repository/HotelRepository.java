package com.example.hotelapp.repository;

import com.example.hotelapp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Hotel findByUsername(String username);
}