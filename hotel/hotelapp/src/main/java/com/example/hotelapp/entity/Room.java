package com.example.hotelapp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Represents a room in a hotel.
 * Each room has a unique identifier, a room type (e.g., Single, Double, Suite), a price, and is associated with a specific hotel.
 * The room can be available or unavailable for booking, and it can have multiple bookings associated with it.
 */
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType; // e.g., Single, Double, Suite
    // private String roomPicture; // File path or URL of the room picture (optional)
    private double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel; // Many rooms belong to one hotel

    @Column(nullable = false)
    private boolean available; // Availability of the room

    private String roomPicture;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

}