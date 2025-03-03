package com.example.hotelapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelapp.entity.Room;
import com.example.hotelapp.repository.RoomRepository;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private FileStorageService fileStorageService;
    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElse(null);
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
    /////// get only available rooms
    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }
    ////////////////////////////////// hotel room
    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
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
    
}
