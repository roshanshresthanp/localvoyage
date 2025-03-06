package com.example.hotelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelapp.entity.Admin;
import com.example.hotelapp.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
    public Admin validateAdminLogin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
    public boolean isAdmin(String username) {
        return adminRepository.findByUsername(username) != null;
    }
    public Admin updateAdminProfile(Long id, String firstName, String lastName, String profilePicture) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setProfilePicture(profilePicture);
            return adminRepository.save(admin);
        }
        return null;}
}