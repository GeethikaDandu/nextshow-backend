package com.nextshow.service;

import com.nextshow.entity.Booking;
import com.nextshow.entity.User;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.BookingRepository;
import com.nextshow.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RewardsService rewardsService;

    public PaymentService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            RewardsService rewardsService) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.rewardsService = rewardsService;
    }

    @Transactional
    public Booking makePayment(
            Long bookingId,
            String paymentMethod) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingException("Booking not found"));

        // Check ownership
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BookingException(
                    "You are not allowed to pay for this booking");
        }

        // Prevent duplicate payment
        if ("PAID".equalsIgnoreCase(
                booking.getPaymentStatus())) {

            throw new BookingException(
                    "Booking is already paid");
        }

        // Only PhonePe for our current demo
        if (!"PHONEPE".equalsIgnoreCase(paymentMethod)) {

            throw new BookingException(
                    "Only PHONEPE payment is supported");
        }

        // -----------------------------
        // PAYMENT SUCCESS
        // -----------------------------

        booking.setPaymentStatus("PAID");

        Booking savedBooking =
                bookingRepository.save(booking);

        // -----------------------------
        // REWARD AFTER PAYMENT
        // -----------------------------

        rewardsService.earnBronze(user.getId());

        return savedBooking;
    }
}