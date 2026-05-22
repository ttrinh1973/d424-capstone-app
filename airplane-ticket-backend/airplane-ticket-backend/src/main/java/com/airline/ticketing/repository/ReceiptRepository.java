package com.airline.ticketing.repository;

import com.airline.ticketing.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {}
