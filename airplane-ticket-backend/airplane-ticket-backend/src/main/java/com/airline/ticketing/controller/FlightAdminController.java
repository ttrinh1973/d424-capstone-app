package com.airline.ticketing.controller;

import com.airline.ticketing.model.Flight;
import com.airline.ticketing.model.User;
import com.airline.ticketing.repository.BookingRepository;
import com.airline.ticketing.repository.FlightRepository;
import com.airline.ticketing.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flights")
@CrossOrigin(origins = "http://localhost:4200")
public class FlightAdminController {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;


    private boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }


    @GetMapping
    public ResponseEntity<?> getAllFlights(@RequestParam String email) {

        if (!isAdmin(email)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        List<Flight> flights = flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }


    @PostMapping
    public ResponseEntity<?> addFlight(
            @RequestParam String email,
            @RequestBody Flight flight) {

        if (!isAdmin(email)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        return ResponseEntity.ok(flightRepository.save(flight));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateFlight(
            @RequestParam String email,
            @PathVariable Long id,
            @RequestBody Flight updatedFlight) {

        if (!isAdmin(email)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        return flightRepository.findById(id)
                .map(flight -> {

                    flight.setAirline(updatedFlight.getAirline());
                    flight.setDeparture(updatedFlight.getDeparture());
                    flight.setDestination(updatedFlight.getDestination());
                    flight.setDepartureTime(updatedFlight.getDepartureTime());
                    flight.setArrivalTime(updatedFlight.getArrivalTime());
                    flight.setPrice(updatedFlight.getPrice());

                    flightRepository.save(flight);

                    return ResponseEntity.ok("Flight updated successfully");
                })
                .orElse(ResponseEntity.status(404).body("Flight not found"));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteFlight(
            @RequestParam String email,
            @PathVariable Long id) {

        if (!isAdmin(email)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        return flightRepository.findById(id)
                .map(flight -> {

                    // STEP 1: delete bookings first (safe FK handling)
                    bookingRepository.deleteAll(
                            bookingRepository.findByFlight_Id(id)
                    );

                    // STEP 2: delete flight
                    flightRepository.deleteById(id);

                    return ResponseEntity.ok("Flight deleted successfully");
                })
                .orElse(ResponseEntity.status(404).body("Flight not found"));
    }
}