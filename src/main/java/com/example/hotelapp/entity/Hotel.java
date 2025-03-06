package com.example.hotelapp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Hotel extends User {
    private String hotelName;
    private String address;
    private String contactNumber;

    public Hotel() {
        this.setRole("HOTEL");
    }
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms; // One hotel can have many rooms

    @OneToMany(mappedBy = "hotel")
    private List<Experience> experiences;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews; // One hotel can have many reviews
}