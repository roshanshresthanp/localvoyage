package com.example.hotelapp.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // ADMIN or HOTEL
}
