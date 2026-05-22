package com.airline.ticketing.service;

import com.airline.ticketing.model.Receipt;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class PdfService {

  public void generateReceiptPdf(Receipt receipt, OutputStream outputStream) throws Exception {

    Document document = new Document();
    PdfWriter.getInstance(document, outputStream);

    document.open();


    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);


    document.add(new Paragraph("✈ Airline Ticket Receipt", titleFont));
    document.add(new Paragraph(" "));


    document.add(new Paragraph("Receipt ID: " + receipt.getId(), normalFont));
    document.add(new Paragraph("Status: " + receipt.getStatus(), normalFont));
    document.add(new Paragraph("Message: " + receipt.getMessage(), normalFont));

    document.add(new Paragraph(" "));
    document.add(new Paragraph("Thank you for choosing our airline!", normalFont));

    document.close();
  }
}
