package com.example.hotelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.repository.ExperienceRepository;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    // Method to get all experiences
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    // Method to get experiences by hotel ID
    public List<Experience> getExperiencesByHotelId(Long hotelId) {
        return experienceRepository.findByHotel_HotelId(hotelId);
    }

    //Get experience by id
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }
    

    // Method to save a new experience
    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    // Method to update an existing experience
    public Experience updateExperience(Long id, Experience experience) {
        experience.setId(id);
        return experienceRepository.save(experience);
    }

    // Method to delete an experience
    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}