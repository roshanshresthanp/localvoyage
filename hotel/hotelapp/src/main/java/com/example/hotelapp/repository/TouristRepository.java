package com.example.hotelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelapp.entity.Tourist;



public interface  TouristRepository extends JpaRepository<Tourist, Long>{

    Tourist findByUsername(String username);
    
}
