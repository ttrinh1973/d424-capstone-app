package com.airline.ticketing.controller;

import com.airline.ticketing.model.Flight;
import com.airline.ticketing.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:4200")
public class FlightController {

  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }


  @GetMapping
  public List<Flight> getAllFlights() {
    return flightService.getAllFlights();
  }


  @GetMapping("/{id}")
  public Flight getFlightById(@PathVariable Long id) {
    return flightService.getFlightById(id);
  }


  @GetMapping("/search")
  public List<Flight> searchFlights(
    @RequestParam(required = false, defaultValue = "") String departure,
    @RequestParam(required = false, defaultValue = "") String destination) {

    return flightService.searchFlights(departure, destination);
  }
}
