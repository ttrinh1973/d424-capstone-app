package com.airline.ticketing.controller;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {


  @PostMapping("/checkout")
  public String createCheckout(@RequestParam Long flightId,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam Double amount) throws Exception {

    SessionCreateParams params =
      SessionCreateParams.builder()
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("http://localhost:4200/success")
        .setCancelUrl("http://localhost:4200/cancel")
        .putMetadata("flightId", flightId.toString())
        .putMetadata("name", name)
        .putMetadata("email", email)

        .addLineItem(
          SessionCreateParams.LineItem.builder()
            .setQuantity(1L)
            .setPriceData(
              SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long) (amount * 100))
                .setProductData(
                  SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName("Flight Ticket")
                    .build()
                )
                .build()
            )
            .build()
        )
        .build();

    Session session = Session.create(params);

    return session.getUrl();
  }
}
