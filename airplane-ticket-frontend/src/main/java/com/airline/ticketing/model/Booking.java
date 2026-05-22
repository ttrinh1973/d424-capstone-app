package com.airline.ticketing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String passengerName;

  @NotBlank
  private String email;

  @ManyToOne
  private Flight flight;

  private String status;
}
