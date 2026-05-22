package com.airline.ticketing.controller;

import com.airline.ticketing.model.Booking;
import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.BookingRepository;
import com.airline.ticketing.repository.FlightRepository;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*")
public class StripeWebhookController {

  private final BookingRepository bookingRepo;
  private final FlightRepository flightRepo;


  private static final String WEBHOOK_SECRET = "whsec_xxxxxxxxxxxxx";

  public StripeWebhookController(BookingRepository b, FlightRepository f) {
    this.bookingRepo = b;
    this.flightRepo = f;
  }

  @PostMapping("/webhook")
  public String handleStripeWebhook(@RequestBody String payload,
                                    @RequestHeader("Stripe-Signature") String sigHeader) {

    try {
      Event event = Webhook.constructEvent(payload, sigHeader, WEBHOOK_SECRET);


      if ("checkout.session.completed".equals(event.getType())) {

        Session session = (Session) event.getDataObjectDeserializer()
          .getObject()
          .orElse(null);

        if (session == null) return "ignored";


        Map<String, String> meta = session.getMetadata();

        Long flightId = Long.parseLong(meta.get("flightId"));
        String name = meta.get("name");
        String email = meta.get("email");


        Flight flight = flightRepo.findById(flightId)
          .orElseThrow(() -> new RuntimeException("Flight not found"));


        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassengerName(name);
        booking.setEmail(email);

        bookingRepo.save(booking);

        System.out.println("✅ Booking created from Stripe webhook!");
      }

      return "success";

    } catch (Exception e) {
      System.out.println("❌ Webhook error: " + e.getMessage());
      return "error";
    }
  }
}
