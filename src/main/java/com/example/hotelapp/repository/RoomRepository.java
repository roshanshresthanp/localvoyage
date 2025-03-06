package com.example.hotelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelapp.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId); // Find rooms by hotel ID
    List<Room> findByAvailableTrue(); // Find available rooms
}