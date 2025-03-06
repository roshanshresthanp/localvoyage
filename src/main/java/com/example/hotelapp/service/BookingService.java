package com.example.hotelapp.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.Booking;
import com.example.hotelapp.entity.Room;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TouristService touristService;

    @Autowired
    private RoomService roomService;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking bookRoom(Long touristId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Tourist tourist = touristService.getTouristById(touristId);
        Room room = roomService.getRoomById(roomId);
        if (tourist == null || room == null) {
            throw new IllegalArgumentException("Tourist or Room not found");
        }
         // Calculate the number of nights
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        // Calculate total price as double
        double roomPrice = room.getPrice(); // Assuming room.getPrice() returns a double
        double totalPrice = roomPrice * numberOfNights;

        Booking booking = new Booking();
        booking.setTourist(tourist);
        booking.setRoom(room);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(totalPrice);
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByTouristId(Long touristId) {
        return bookingRepository.findByTouristId(touristId);
    }

    public List<Booking> getBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    // booking status updating
    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public Long getHotelIdByRoomId(Long roomId) {
        return bookingRepository.findHotelIdByRoomId(roomId);
    }

    public List<Booking> getBookingsByHotelId(Long hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public long getTotalHotelBookings() {
        return bookingRepository.count();
        
    }


}