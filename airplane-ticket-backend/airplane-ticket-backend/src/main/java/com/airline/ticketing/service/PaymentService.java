package com.airline.ticketing.service;

import com.airline.ticketing.model.*;
import com.airline.ticketing.repository.*;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepo;
  private final ReceiptRepository receiptRepo;
  private final BookingRepository bookingRepo;

  public PaymentService(PaymentRepository p, ReceiptRepository r, BookingRepository b) {
    this.paymentRepo = p;
    this.receiptRepo = r;
    this.bookingRepo = b;
  }

  public Receipt processStripePayment(Long bookingId, Double amount) throws Exception {

    Booking booking = bookingRepo.findById(bookingId)
      .orElseThrow(() -> new RuntimeException("Booking not found"));


    long amountInCents = (long) (amount * 100);

    PaymentIntentCreateParams params =
      PaymentIntentCreateParams.builder()
        .setAmount(amountInCents)
        .setCurrency("usd")
        .setAutomaticPaymentMethods(
          PaymentIntentCreateParams.AutomaticPaymentMethods
            .builder()
            .setEnabled(true)
            .build()
        )
        .build();

    PaymentIntent intent = PaymentIntent.create(params);

    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setAmount(amount);
    payment.setStatus(intent.getStatus());
    payment.setCardHolder("Stripe Payment");
    payment.setCardNumber("N/A");
    payment.setExpiry("N/A");

    payment = paymentRepo.save(payment);

    Receipt receipt = new Receipt();
    receipt.setPayment(payment);
    receipt.setStatus(intent.getStatus());
    receipt.setMessage("Stripe Payment Created: " + intent.getId());

    return receiptRepo.save(receipt);
  }
}
