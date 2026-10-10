package com.nextshow.controller;

import com.nextshow.dto.PaymentRequest;
import com.nextshow.entity.Booking;
import com.nextshow.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<Booking> makePayment(
            @PathVariable Long bookingId,
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.makePayment(
                        bookingId,
                        request.getPaymentMethod()
                )
        );
    }
}