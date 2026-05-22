package com.airline.ticketing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Airline is required")
  private String airline;

  @NotBlank(message = "Departure is required")
  private String departure;

  @NotBlank(message = "Destination is required")
  private String destination;

  @NotBlank(message = "Departure time is required")
  private String departureTime;

  @NotBlank(message = "Arrival time is required")
  private String arrivalTime;

  @NotNull(message = "Price is required")
  @Min(value = 1, message = "Price must be greater than 0")
  private Double price;


}
