package com.nextshow.repository;

import com.nextshow.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByBookingId(Long bookingId);

    Optional<Ticket> findByTicketNumber(String ticketNumber);
}