package com.example.hotelapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.ContactForm;
import com.example.hotelapp.repository.ContactFormRepository;

@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    public ContactForm saveContactForm(ContactForm contactForm) {
        contactForm.setCreatedAt(LocalDateTime.now());
        return contactFormRepository.save(contactForm);
    }

    public List<ContactForm> getAllConatctForm() {
        return contactFormRepository.findAll();
    }

    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }
}
