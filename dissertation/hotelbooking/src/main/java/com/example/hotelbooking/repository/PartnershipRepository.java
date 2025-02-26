package com.example.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.model.Partnership;

public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
}