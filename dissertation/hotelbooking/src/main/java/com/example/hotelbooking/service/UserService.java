package com.example.hotelbooking.service;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Role;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    

    @Autowired
    private FileStorageService fileStorageService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public User saveOrUpdateUser(User user, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            user.setProfilePicturePath(fileName);
        } else if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                user.setProfilePicturePath(existingUser.getProfilePicturePath());
            }
        }

        // Encode the password before saving
        if (user.getPassword() != null) {
            user.setPassword(user.getPassword());
        }

        return userRepository.save(user);
}
    

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
      // Get users with 'USER' role
    public List<User> findUsersByRole() {
        // Pass the Role enum to the repository method
        return userRepository.findByRole(Role.USER);
    }


    // Hotel //////////////////
    @Autowired
    private HotelRepository hotelRepository;

    public User createHotelUser(String name, String username, String password, String hotelName, String location) {
        // Create a new user with the HOTEL role
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.HOTEL);

        // Create a new hotel
        Hotel hotel = new Hotel();
        hotel.setName(hotelName);
        hotel.setLocation(location);
        hotel.setUser(user); // Link the hotel to the user

        // Save the user and hotel
        user.setHotel(hotel);
        userRepository.save(user);
        hotelRepository.save(hotel);

        return user;
    }
}