package com.nextshow.controller;

import com.nextshow.dto.TicketQrResponse;
import com.nextshow.dto.TicketVerificationResponse;
import com.nextshow.entity.Ticket;
import com.nextshow.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<Ticket> generateTicket(
            @PathVariable Long bookingId) {

        Ticket ticket = ticketService.generateTicket(bookingId);

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Ticket> getTicket(
            @PathVariable Long bookingId) {

        Ticket ticket = ticketService.getTicketByBookingId(bookingId);

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/qr/{bookingId}")
    public ResponseEntity<TicketQrResponse> generateTicketQr(
            @PathVariable Long bookingId) {

        TicketQrResponse response =
                ticketService.generateTicketQr(bookingId);

        return ResponseEntity.ok(response);
    }

    // STAFF SCANS QR AND VERIFIES TICKET
    @GetMapping("/verify/{ticketNumber}")
    public ResponseEntity<TicketVerificationResponse> verifyTicket(
            @PathVariable String ticketNumber) {

        TicketVerificationResponse response =
                ticketService.verifyTicket(ticketNumber);

        return ResponseEntity.ok(response);
    }

    // STAFF ALLOWS ENTRY AND MARKS TICKET AS USED
    @PutMapping("/use/{ticketNumber}")
    public ResponseEntity<TicketVerificationResponse> useTicket(
            @PathVariable String ticketNumber) {

        TicketVerificationResponse response =
                ticketService.useTicket(ticketNumber);

        return ResponseEntity.ok(response);
    }
}