package com.airline.ticketing.service;

import com.airline.ticketing.model.Booking;
import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.BookingRepository;
import com.airline.ticketing.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  @Autowired
  private BookingRepository repo;

  @Autowired
  private FlightRepository flightRepo;

  public Booking createBooking(Long flightId, Booking booking) {
    Flight flight = flightRepo.findById(flightId).orElseThrow();
    booking.setFlight(flight);
    return repo.save(booking);
  }
}
