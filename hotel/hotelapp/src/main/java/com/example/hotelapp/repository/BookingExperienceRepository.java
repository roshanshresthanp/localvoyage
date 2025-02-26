package com.example.hotelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotelapp.entity.BookingExperience;

public interface BookingExperienceRepository extends JpaRepository <BookingExperience,Long>{
    List<BookingExperience> findByTouristId(Long touristId);
    List<BookingExperience> findByExperience_Id(Long experienceId);
    // List<Booking> findByHotelId(Long hotelId);
    @Query("SELECT b.experience.hotel.id FROM BookingExperience b WHERE b.experience.id = :experienceId")
    Long findHotelIdByBookingExperienceId(@Param("experienceId") Long experienceId);

    @Query("SELECT b FROM BookingExperience b WHERE b.experience.hotel.id = :hotelId")
    List<BookingExperience> findBookingExperienceByHotelId(@Param("hotelId") Long hotelId);
}
