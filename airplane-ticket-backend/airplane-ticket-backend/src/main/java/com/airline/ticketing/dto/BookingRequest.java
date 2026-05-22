package com.airline.ticketing.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
  private Long flightId;
  private String passengerName;
  private String email;


}
