package com.example.hotelbooking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hotelbooking.model.Role;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.UserRepository;

@Component
public class DefaultAdminUser implements CommandLineRunner {

    private final UserRepository userRepository;


    public DefaultAdminUser(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}