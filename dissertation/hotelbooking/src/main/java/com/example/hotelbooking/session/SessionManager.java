package com.example.hotelbooking.session;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.hotelbooking.model.User;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionManager {

    private final Map<String, HttpSession> activeSessions = new HashMap<>();

    public void createSession(User user, HttpSession session) {
        session.setAttribute("user", user);
        activeSessions.put(user.getUsername(), session);
    }

    public void invalidateSession(String username) {
        HttpSession session = activeSessions.get(username);
        if (session != null) {
            session.invalidate();
            activeSessions.remove(username);
        }
    }

    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}