package com.airline.ticketing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
  public Long bookingId;
  public String cardHolder;
  public String cardNumber;
  public String expiry;
  public Double amount;
}
