package com.airline.ticketing.config;

import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

  @Bean
  CommandLineRunner initDatabase(FlightRepository flightRepository) {
    return args -> {

      if (flightRepository.count() == 0) {

        flightRepository.save(createFlight("Delta Airlines", "New York", "Los Angeles", "07:00 AM", "10:30 AM", 299.99));
        flightRepository.save(createFlight("American Airlines", "Chicago", "Miami", "09:00 AM", "01:15 PM", 199.99));
        flightRepository.save(createFlight("United Airlines", "Seattle", "San Francisco", "03:00 PM", "06:45 PM", 149.99));
        flightRepository.save(createFlight("Southwest Airlines", "Dallas", "Denver", "06:00 AM", "09:20 AM", 129.99));
        flightRepository.save(createFlight("JetBlue", "Boston", "Orlando", "08:30 PM", "11:50 PM", 179.99));
        flightRepository.save(createFlight("Alaska Airlines", "Los Angeles", "Seattle", "06:00 AM", "08:45 AM", 189.99));
        flightRepository.save(createFlight("Spirit Airlines", "Miami", "New York", "10:00 AM", "01:30 PM", 159.99));
        flightRepository.save(createFlight("Frontier Airlines", "Denver", "Las Vegas", "02:00 PM", "03:20 PM", 99.99));
        flightRepository.save(createFlight("Hawaiian Airlines", "Honolulu", "Los Angeles", "09:00 PM", "05:30 AM", 399.99));
        flightRepository.save(createFlight("Allegiant Air", "Orlando", "Pittsburgh", "07:15 AM", "09:45 AM", 119.99));
        flightRepository.save(createFlight("Delta Airlines", "Atlanta", "Chicago", "05:30 AM", "07:15 AM", 149.99));
        flightRepository.save(createFlight("United Airlines", "San Francisco", "New York", "11:00 AM", "07:30 PM", 349.99));
        flightRepository.save(createFlight("American Airlines", "Dallas", "Los Angeles", "04:00 PM", "06:00 PM", 259.99));
        flightRepository.save(createFlight("JetBlue", "New York", "Boston", "12:00 PM", "01:30 PM", 89.99));
        flightRepository.save(createFlight("Southwest Airlines", "Las Vegas", "Phoenix", "08:00 AM", "09:10 AM", 79.99));

        System.out.println("✅ 15 sample flights inserted into MySQL database!");
      }
    };
  }

  private Flight createFlight(
    String airline,
    String departure,
    String destination,
    String departureTime,
    String arrivalTime,
    double price
  ) {
    Flight flight = new Flight();
    flight.setAirline(airline);
    flight.setDeparture(departure);
    flight.setDestination(destination);
    flight.setDepartureTime(departureTime);
    flight.setArrivalTime(arrivalTime);
    flight.setPrice(price);
    return flight;
  }
}
