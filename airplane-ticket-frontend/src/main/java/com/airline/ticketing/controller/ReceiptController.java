package com.airline.ticketing.controller;

import com.airline.ticketing.model.Receipt;
import com.airline.ticketing.repository.ReceiptRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "http://localhost:4200")
public class ReceiptController {

  private final ReceiptRepository receiptRepository;

  public ReceiptController(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }


  @GetMapping("/{id}")
  public Receipt getReceipt(@PathVariable Long id) {
    return receiptRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Receipt not found"));
  }


  @PostMapping
  public Receipt createReceipt(@RequestBody Receipt receipt) {
    return receiptRepository.save(receipt);
  }
}
