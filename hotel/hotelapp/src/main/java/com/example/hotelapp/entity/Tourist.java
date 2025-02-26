package com.example.hotelapp.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Tourist extends User {

    private String firstName;
    private String lastName;
    private String touristPicture;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth; 

    private String gender;
     @Column(unique = true, nullable = false) // username is unique
    private String username;

    @Column(nullable = false) // Ensure password is not null
    private String password;

    public Tourist() {
        this.setRole("TOURIST");
    }
    
    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
