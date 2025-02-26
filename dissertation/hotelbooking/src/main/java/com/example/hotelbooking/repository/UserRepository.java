package com.example.hotelbooking.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.model.Role;
import com.example.hotelbooking.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
      // Get users with 'USER' role
      List<User> findByRole(Role role);

}
