

package com.example.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
