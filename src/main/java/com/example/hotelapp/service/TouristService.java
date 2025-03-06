package com.example.hotelapp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.repository.TouristRepository;

@Service
public class TouristService {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<Tourist> getAllTourist(){
        return touristRepository.findAll();
    }

    public Tourist getTouristById(Long id){
        return touristRepository.findById(id).orElse(null);
    }

    public Tourist saveTourist(Tourist tourist, MultipartFile file) throws IOException {
        // Check if the tourist has an ID for updating
        if (tourist.getId() != null) {
            Tourist existingTourist = touristRepository.findById(tourist.getId()).orElse(null);
            if (existingTourist != null) {
                System.out.println("Updating existing tourist with ID: " + tourist.getId());
                // Update fields of the existing tourist
                existingTourist.setFirstName(tourist.getFirstName());
                existingTourist.setLastName(tourist.getLastName());
                existingTourist.setId(tourist.getId());
                // Handle file upload
                if (file != null && !file.isEmpty()) {
                    // Save the new file
                    String fileName = fileStorageService.storeFile(file);
                    existingTourist.setTouristPicture(fileName);
                }
                // Handle password update
                if (tourist.getPassword() != null && !tourist.getPassword().isEmpty()) {
                    existingTourist.setPassword(tourist.getPassword());
                }
                // Save the updated tourist
                return touristRepository.save(existingTourist);
            } else {
                System.out.println("No existing tourist found with ID: " + tourist.getId());
            }
        } else {
            System.out.println("Creating new tourist.");
        }
        // Handle new tourist creation
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            tourist.setTouristPicture(fileName);
        }
        if (tourist.getPassword() != null && !tourist.getPassword().isEmpty()) {
            tourist.setPassword(tourist.getPassword());
        }
        // Save the new tourist
        return touristRepository.save(tourist);
    }

    public Tourist updateTourist(Long id, Tourist tourist, MultipartFile file) throws IOException {
        Tourist existingTourist = touristRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tourist not found"));
        // Update attributes
        existingTourist.setFirstName(tourist.getFirstName());
        existingTourist.setLastName(tourist.getLastName());
        existingTourist.setDateOfBirth(tourist.getDateOfBirth());
        existingTourist.setGender(tourist.getGender());
        existingTourist.setUsername(tourist.getUsername());

        // Handle file upload if a new file is provided
        if (file != null && !file.isEmpty()) {
            // Logic to handle file upload (e.g., save the file and set the path)
            String fileName = file.getOriginalFilename(); // Simplified for example
            existingTourist.setTouristPicture(fileName);
        }

        return touristRepository.save(existingTourist);
    }

    public void deleteTourist(Long id) {
        touristRepository.deleteById(id);
    }

    public Tourist validateTouristLogin(String username, String password) {
         Tourist tourist = touristRepository.findByUsername(username);
        if (tourist != null && tourist.getPassword().equals(password)) {
            return tourist;
        }
        return null;
    }

    public boolean isTourist(String username) {
        return touristRepository.findByUsername(username) != null;
    }

    // to update profile of tourist
    public Tourist updateTouristProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender,String username,String profilePicture) {
        Tourist tourist = touristRepository.findById(id).orElse(null);
        if (tourist != null) {
            tourist.setFirstName(firstName);
            tourist.setLastName(lastName);
            tourist.setTouristPicture(profilePicture);
            tourist.setDateOfBirth(dateOfBirth); // Update dateOfBirth
            tourist.setGender(gender); // Update gender
            tourist.setUsername(username);

            return touristRepository.save(tourist);
        }
        return null;
    }

    public Tourist saveTouristPass(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    public long getTotalTourists() {
        return touristRepository.count();
       
    }
}
