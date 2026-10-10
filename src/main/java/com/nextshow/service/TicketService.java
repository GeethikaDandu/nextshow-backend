package com.nextshow.service;

import com.nextshow.dto.TicketQrResponse;
import com.nextshow.dto.TicketVerificationResponse;
import com.nextshow.entity.Booking;
import com.nextshow.entity.Ticket;
import com.nextshow.entity.User;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.BookingRepository;
import com.nextshow.repository.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final QrCodeService qrCodeService;

    public TicketService(
            TicketRepository ticketRepository,
            BookingRepository bookingRepository,
            QrCodeService qrCodeService) {

        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.qrCodeService = qrCodeService;
    }

    @Transactional
    public Ticket generateTicket(Long bookingId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = booking.getUser();

        if (!user.getEmail().equals(email)) {
            throw new BookingException(
                    "You are not allowed to generate this ticket"
            );
        }

        if (!"PAID".equals(booking.getPaymentStatus())) {
            throw new BookingException(
                    "Payment must be completed before generating ticket"
            );
        }

        if (ticketRepository.findByBookingId(bookingId).isPresent()) {
            throw new BookingException(
                    "Ticket has already been generated"
            );
        }

        Ticket ticket = new Ticket();

        ticket.setBooking(booking);

        ticket.setTicketNumber(
                "NS-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        ticket.setStatus("ACTIVE");
        ticket.setGeneratedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public Ticket getTicketByBookingId(Long bookingId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Ticket ticket = ticketRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getBooking().getUser().getEmail().equals(email)) {
            throw new BookingException(
                    "You are not allowed to view this ticket"
            );
        }

        return ticket;
    }

    public TicketQrResponse generateTicketQr(Long bookingId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Ticket ticket = ticketRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getBooking().getUser().getEmail().equals(email)) {
            throw new BookingException(
                    "You are not allowed to view this ticket"
            );
        }

        if (!"ACTIVE".equals(ticket.getStatus())) {
            throw new BookingException("Ticket is not active");
        }

        String qrData = ticket.getTicketNumber();

        String qrCode = qrCodeService.generateQrCode(qrData);

        return new TicketQrResponse(
                ticket.getId(),
                ticket.getTicketNumber(),
                qrCode
        );
    }

    // STAFF VERIFICATION
    @Transactional
    public TicketVerificationResponse verifyTicket(String ticketNumber) {

        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() ->
                        new BookingException("Invalid ticket"));

        Booking booking = ticket.getBooking();

        if (!"ACTIVE".equals(ticket.getStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Ticket has already been used or is inactive",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        if (!"PAID".equals(booking.getPaymentStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Payment is not completed",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        if (!"CONFIRMED".equals(booking.getStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Booking is not confirmed",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        return new TicketVerificationResponse(
                true,
                "VALID TICKET",
                ticket.getTicketNumber(),
                booking.getUser().getName(),
                booking.getShow().getEvent().getTitle(),
                booking.getShow().getScreen().getVenue().getName(),
                booking.getShow().getShowDate().toString(),
                booking.getShow().getShowTime().toString(),
                ticket.getStatus()
        );
    }

    // STAFF USES TICKET AFTER ENTRY
    @Transactional
    public TicketVerificationResponse useTicket(String ticketNumber) {

        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() ->
                        new BookingException("Invalid ticket"));

        Booking booking = ticket.getBooking();

        if (!"ACTIVE".equals(ticket.getStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Ticket has already been used",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        if (!"PAID".equals(booking.getPaymentStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Payment is not completed",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        if (!"CONFIRMED".equals(booking.getStatus())) {

            return new TicketVerificationResponse(
                    false,
                    "Booking is not confirmed",
                    ticket.getTicketNumber(),
                    booking.getUser().getName(),
                    booking.getShow().getEvent().getTitle(),
                    booking.getShow().getScreen().getVenue().getName(),
                    booking.getShow().getShowDate().toString(),
                    booking.getShow().getShowTime().toString(),
                    ticket.getStatus()
            );
        }

        ticket.setStatus("USED");
        ticketRepository.save(ticket);

        return new TicketVerificationResponse(
                true,
                "Ticket verified and entry allowed",
                ticket.getTicketNumber(),
                booking.getUser().getName(),
                booking.getShow().getEvent().getTitle(),
                booking.getShow().getScreen().getVenue().getName(),
                booking.getShow().getShowDate().toString(),
                booking.getShow().getShowTime().toString(),
                ticket.getStatus()
        );
    }
}