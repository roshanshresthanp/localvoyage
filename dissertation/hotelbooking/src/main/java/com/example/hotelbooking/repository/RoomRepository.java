package com.example.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}