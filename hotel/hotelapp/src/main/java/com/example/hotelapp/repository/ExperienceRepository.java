package com.example.hotelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hotelapp.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("SELECT e FROM Experience e WHERE e.hotel.id = :hotelId")
    List<Experience> findByHotel_HotelId(@Param("hotelId") Long hotelId); // Find experiences by hotel ID
}