package com.airline.ticketing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double amount;

  private String cardHolder;

  private String cardNumber;

  private String expiry;

  private String status;

  @OneToOne
  private Booking booking;
}
