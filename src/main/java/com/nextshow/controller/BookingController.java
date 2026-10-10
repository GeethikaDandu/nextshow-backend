package com.nextshow.controller;

import com.nextshow.dto.CreateBookingRequest;
import com.nextshow.entity.Booking;
import com.nextshow.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        Booking booking =
                bookingService.createBooking(request);

        return ResponseEntity.ok(booking);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Long bookingId) {

        Booking cancelledBooking =
                bookingService.cancelBooking(bookingId);

        return ResponseEntity.ok(cancelledBooking);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getMyBookings() {

        List<Booking> bookings =
                bookingService.getMyBookings();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(
            @PathVariable Long bookingId) {

        Booking booking =
                bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(booking);
    }
}