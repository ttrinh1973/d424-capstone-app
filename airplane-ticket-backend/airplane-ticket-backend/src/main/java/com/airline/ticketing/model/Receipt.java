package com.airline.ticketing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Receipt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  private String status;

  @OneToOne
  private Payment payment;

  public void setPaymentId(Long id) {
  }
}
