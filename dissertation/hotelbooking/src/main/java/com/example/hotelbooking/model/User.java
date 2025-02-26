package com.example.hotelbooking.model;




import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // private String name;
    // private String profilePicturePath;
    
    // @Past(message = "Date of Birth must be in the past")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private LocalDate dateOfBirth; 

    // private String gender;
    // private String ageGroup;
     @Column(unique = true, nullable = false) // username is unique
    private String username;

    @Column(nullable = false) // Ensure password is not null
    private String password;

    @Enumerated(EnumType.STRING) // Store the role as a string in the database
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Hotel hotel;
}