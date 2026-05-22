package com.airline.ticketing.repository;

import com.airline.ticketing.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

  @Query("SELECT f FROM Flight f " +
    "WHERE LOWER(f.departure) LIKE LOWER(CONCAT('%', :departure, '%')) " +
    "AND LOWER(f.destination) LIKE LOWER(CONCAT('%', :destination, '%'))")
  List<Flight> searchFlights(@Param("departure") String departure,
                             @Param("destination") String destination);
}
