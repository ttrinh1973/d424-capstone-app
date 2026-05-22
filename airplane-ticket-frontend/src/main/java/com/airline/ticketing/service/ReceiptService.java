package com.airline.ticketing.service;

import com.airline.ticketing.model.Payment;
import com.airline.ticketing.model.Receipt;
import com.airline.ticketing.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

  @Autowired
  private ReceiptRepository repo;

  public Receipt generateReceipt(Payment payment) {
    Receipt receipt = new Receipt();
    receipt.setPayment(payment);
    receipt.setStatus("SUCCESS");
    receipt.setMessage("Payment Successful");
    return repo.save(receipt);
  }
}
