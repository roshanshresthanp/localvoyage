package com.example.hotelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelapp.entity.ContactForm;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
    
}
