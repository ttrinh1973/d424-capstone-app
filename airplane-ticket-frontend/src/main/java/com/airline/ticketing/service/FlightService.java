package com.airline.ticketing.service;

import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }


  public List<Flight> getAllFlights() {
    return flightRepository.findAll();
  }


  public List<Flight> searchFlights(String departure, String destination) {
    return flightRepository.searchFlights(departure.trim(), destination.trim());
  }


  public Flight getFlightById(Long id) {
    return flightRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
  }


  public Flight addFlight(Flight flight) {
    return flightRepository.save(flight);
  }
}
