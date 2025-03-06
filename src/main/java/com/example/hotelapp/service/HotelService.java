package com.example.hotelapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelapp.entity.Hotel;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.repository.HotelRepository;
import com.example.hotelapp.repository.RoomRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RoomRepository roomRepository;

    public Hotel getHotelByUsername(String username) {
        return hotelRepository.findByUsername(username);
    }

    public Hotel updateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel validateHotelLogin(String username, String password) {
        Hotel hotel = hotelRepository.findByUsername(username);
        if (hotel != null && hotel.getPassword().equals(password)) {
            return hotel;
        }
        return null;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);

        
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public boolean isHotel(String username) {
        return hotelRepository.findByUsername(username) != null;
    }

    // room ///////////////////////////////////
    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }
    
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
    
    public Room saveRoom(Room room,  MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            room.setRoomPicture(fileName);
            
        }
        
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room room, MultipartFile file) throws IOException {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        
        // Update attributes
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setHotel(room.getHotel());
        existingRoom.setPrice(room.getPrice()); 
        existingRoom.setAvailable(room.isAvailable());
        

        // Handle file upload if a new file is provided
        if (file != null && !file.isEmpty()) {
            // Logic to handle file upload (e.g., save the file and set the path)
            String fileName = file.getOriginalFilename(); // Simplified for example
            existingRoom.setRoomPicture(fileName);
        }

        return roomRepository.save(existingRoom);
    }
    

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public long getTotalHotel() {
        return hotelRepository.count();
        
    }
}