package com.airline.ticketing.controller;

import com.airline.ticketing.model.Receipt;
import com.airline.ticketing.repository.ReceiptRepository;
import com.airline.ticketing.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "http://localhost:4200")
public class ReceiptPdfController {

  private final ReceiptRepository receiptRepository;
  private final PdfService pdfService;

  public ReceiptPdfController(ReceiptRepository receiptRepository,
                              PdfService pdfService) {
    this.receiptRepository = receiptRepository;
    this.pdfService = pdfService;
  }

  @GetMapping("/receipt/{id}")
  public void downloadReceipt(@PathVariable Long id,
                              HttpServletResponse response) throws Exception {

    Receipt receipt = receiptRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Receipt not found"));

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition",
      "attachment; filename=receipt_" + id + ".pdf");

    pdfService.generateReceiptPdf(receipt, response.getOutputStream());
  }
}
