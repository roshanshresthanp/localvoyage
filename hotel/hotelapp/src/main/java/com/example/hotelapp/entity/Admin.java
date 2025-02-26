package com.example.hotelapp.entity;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Admin extends User {
    private String firstName;
    private String lastName;
    private String profilePicture;
}