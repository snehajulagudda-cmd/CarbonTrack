package com.carbontrack.carbontrack;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User Registration API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null
                || user.getPassword().length() < 8) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Name and email are required; password must have at least 8 characters."
                    ));
        }

        if (userRepository.existsByEmail(user.getEmail())) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error",
                            "Email already registered."
                    ));
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        // Return user details without password
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "id", savedUser.getId(),
                        "name", savedUser.getName(),
                        "email", savedUser.getEmail(),
                        "message", "Registration successful!"
                ));
    }

    // User Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null
                || user.getPassword().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Email and password are required."
                    ));
        }

        // Find user using email
        var existingUser = userRepository.findByEmail(user.getEmail());

        // Check email and password
        if (existingUser.isEmpty()
                || !passwordEncoder.matches(
                        user.getPassword(),
                        existingUser.get().getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error",
                            "Invalid email or password."
                    ));
        }

        User loggedInUser = existingUser.get();

        // Return user details without password
        return ResponseEntity.ok(
                Map.of(
                        "id", loggedInUser.getId(),
                        "name", loggedInUser.getName(),
                        "email", loggedInUser.getEmail(),
                        "message", "Login successful!"
                )
        );
    }
}