package com.airline.ticketing.repository;

import com.airline.ticketing.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    void deleteByFlight_Id(Long id);

    Iterable<? extends Booking> findByFlight_Id(Long id);


}