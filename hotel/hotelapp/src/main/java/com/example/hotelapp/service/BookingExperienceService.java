package com.example.hotelapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.BookingExperience;
import com.example.hotelapp.entity.Experience;
import com.example.hotelapp.entity.Tourist;
import com.example.hotelapp.repository.BookingExperienceRepository;

@Service
public class BookingExperienceService {
    @Autowired
    private BookingExperienceRepository bookingExperienceRepository;

    @Autowired
    private TouristService touristService;

    @Autowired
    private ExperienceService experienceService;

    public BookingExperience bookExperience(Long touristId, Long experienceId, LocalDate experienceDate) {
        Tourist tourist = touristService.getTouristById(touristId);
        Experience experience = experienceService.getExperienceById(experienceId);
        if (tourist == null || experience == null) {
            throw new IllegalArgumentException("Tourist or Experience not found");
        }
        BookingExperience bookingExperience = new BookingExperience();
        bookingExperience.setTourist(tourist);
        bookingExperience.setExperience(experience);
        bookingExperience.setExperienceDate(experienceDate);
        bookingExperience.setStatus("PENDING");

        return bookingExperienceRepository.save(bookingExperience);
    }
    public List<BookingExperience> getBookingsByTouristId(Long touristId) {
        return bookingExperienceRepository.findByTouristId(touristId);
    }
    public List<BookingExperience> getBookingsByExperienceId(Long experienceId) {
        return bookingExperienceRepository.findByExperience_Id(experienceId);
    }
    // booking status updating
    public BookingExperience updateBookingStatus(Long bookingExperienceId, String status) {
        BookingExperience bookingExerience = bookingExperienceRepository.findById(bookingExperienceId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            bookingExerience.setStatus(status);
        return bookingExperienceRepository.save(bookingExerience);
    }
    public Long getHotelIdByExperienceId(Long experienceId) {
        return bookingExperienceRepository.findHotelIdByBookingExperienceId(experienceId);
    }
    public List<BookingExperience> getBookingExperienceByHotelId(Long hotelId) {
        return bookingExperienceRepository.findBookingExperienceByHotelId(hotelId);
    }
    public BookingExperience getBookingById(Long bookingExperienceId) {
        return bookingExperienceRepository.findById(bookingExperienceId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }
    public BookingExperience confirmBookingExperience(Long bookingExperienceId) {
        BookingExperience bookingExperience = bookingExperienceRepository.findById(bookingExperienceId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            bookingExperience.setStatus("CONFIRMED");
        return bookingExperienceRepository.save(bookingExperience);
    }
    public List<BookingExperience> getAllBookingExperiences(){
        return bookingExperienceRepository.findAll();
    }
    public long getTotalExperienceBookings() {
        return bookingExperienceRepository.count();
    }

    public long getHotelExperienceBookings(Long hotelId){
        return bookingExperienceRepository.countExperienceByHotelId(hotelId);
    }

    public long getHotelExperienceBookingsByTourist(Long touristId){
        return bookingExperienceRepository.countExperienceByTouristId(touristId);
    }
}
