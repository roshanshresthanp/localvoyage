package com.example.hotelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotelapp.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTouristId(Long touristId);
    List<Booking> findByRoomId(Long roomId);
    @Query("SELECT b.room.hotel.id FROM Booking b WHERE b.room.id = :roomId")
    Long findHotelIdByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT b FROM Booking b WHERE b.room.hotel.id = :hotelId")
    List<Booking> findByHotelId(@Param("hotelId") Long hotelId);
    
}