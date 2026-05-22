package com.airline.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.airline.ticketing.dto.LoginRequest;
import com.airline.ticketing.dto.LoginResponse;
import com.airline.ticketing.model.User;
import com.airline.ticketing.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

  @Autowired
  private UserRepository userRepository;


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    String email = request.getEmail().trim();
    String password = request.getPassword().trim();

    User user = userRepository.findByEmail(email);

    if (user == null) {
      return ResponseEntity.badRequest().body("Invalid email or password");
    }

    if (!user.getPassword().trim().equals(password)) {
      return ResponseEntity.badRequest().body("Invalid email or password");
    }

    return ResponseEntity.ok(
            new LoginResponse(
                    "AEROAGE_TOKEN",
                    user.getRole(),
                    user.getName()
            )
    );
  }


}
