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

@Data
@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel; // Foreign key linking to the Hotel entity

    @Column(name = "experience_name", nullable = false)
    private String experienceName; // Name of the experience

    @Column(name = "description", nullable = false)
    private String description; // Description of the experience

    @Column(name = "price", nullable = false)
    private double price; // Price of the experience

    @Column(name = "duration", nullable = false)
    private String duration; // Duration of the experience

    @Column(name = "is_local", nullable = false)
    private boolean local; // Indicates if the experience is local

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
