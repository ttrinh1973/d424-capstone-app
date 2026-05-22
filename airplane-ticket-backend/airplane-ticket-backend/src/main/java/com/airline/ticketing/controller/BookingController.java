package com.airline.ticketing.controller;

import com.airline.ticketing.dto.BookingRequest;
import com.airline.ticketing.model.Booking;
import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.BookingRepository;
import com.airline.ticketing.repository.FlightRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

  private final BookingRepository bookingRepository;
  private final FlightRepository flightRepository;

  public BookingController(BookingRepository bookingRepository,
                           FlightRepository flightRepository) {
    this.bookingRepository = bookingRepository;
    this.flightRepository = flightRepository;
  }

  @PostMapping
  public Booking create(@RequestBody BookingRequest req) {

    Flight flight = flightRepository.findById(req.getFlightId())
      .orElseThrow(() -> new RuntimeException("Flight not found"));

    Booking booking = new Booking();
    booking.setPassengerName(req.getPassengerName());
    booking.setEmail(req.getEmail());
    booking.setFlight(flight);
    booking.setStatus("BOOKED");

    return bookingRepository.save(booking);
  }

  @GetMapping
  public List<Booking> getAll() {
    return bookingRepository.findAll();
  }
}
