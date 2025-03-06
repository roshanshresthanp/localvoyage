package com.example.hotelapp.repository;

import com.example.hotelapp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}