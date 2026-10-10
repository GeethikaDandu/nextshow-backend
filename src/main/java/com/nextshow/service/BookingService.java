package com.nextshow.service;

import com.nextshow.dto.CreateBookingRequest;
import com.nextshow.entity.*;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final CouponService couponService;
    private final RewardsService rewardsService;

    public BookingService(
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            UserRepository userRepository,
            CouponService couponService,
            RewardsService rewardsService) {

        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.couponService = couponService;
        this.rewardsService = rewardsService;
    }

    @Transactional
    public Booking createBooking(CreateBookingRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                        new BookingException("Show not found"));

       double totalAmount = 0;
List<ShowSeat> selectedSeats = new ArrayList<>();
List<ShowSeat> freeSeats = new ArrayList<>();
if (request.getShowSeatIds() == null
        || request.getShowSeatIds().isEmpty()) {
    throw new BookingException("Please select at least one ticket");
}

int freeTickets = request.getShowSeatIds().size() / 10;

if (new HashSet<>(request.getShowSeatIds()).size()
        != request.getShowSeatIds().size()) {
    throw new BookingException(
            "Duplicate seat selections are not allowed");
}
        // Check seats
        for (Long showSeatId : request.getShowSeatIds()) {

            ShowSeat showSeat = showSeatRepository
                    .findWithLockById(showSeatId)
                    .orElseThrow(() ->
                            new BookingException(
                                    "Show seat not found"));

            if (!showSeat.getShow().getId()
                    .equals(show.getId())) {

                throw new BookingException(
                        "Seat does not belong to this show");
            }

            if (!"AVAILABLE".equalsIgnoreCase(
                    showSeat.getStatus())) {

                throw new BookingException(
                        "Seat "
                        + showSeat.getSeat().getSeatNumber()
                        + " is already booked");
            }

            totalAmount += showSeat.getPrice() != null
        ? showSeat.getPrice()
        : showSeat.getSeat().getPrice();
            selectedSeats.add(showSeat);
        }
double freeTicketDiscount = 0;

if (freeTickets > 0) {
    List<ShowSeat> cheapestFirst = new ArrayList<>(selectedSeats);

    cheapestFirst.sort(
        java.util.Comparator.comparingDouble(
            seat -> seat.getPrice() != null
        ? seat.getPrice()
        : seat.getSeat().getPrice()
        )
    );

    for (int i = 0; i < freeTickets; i++) {
        ShowSeat freeSeat = cheapestFirst.get(i);
        freeSeats.add(freeSeat);
        freeTicketDiscount += freeSeat.getPrice() != null
        ? freeSeat.getPrice()
        : freeSeat.getSeat().getPrice();
    }
}
        // -----------------------------
        // COUPON
        // -----------------------------

        double couponDiscount = 0;

        if (request.getCouponCode() != null
                && !request.getCouponCode().isBlank()) {

            Coupon coupon =
                    couponService.getCouponByCode(
                            request.getCouponCode());

            if (totalAmount <
                    coupon.getMinimumBookingAmount()) {

                throw new BookingException(
                        "Minimum booking amount for coupon is ₹"
                        + coupon.getMinimumBookingAmount());
            }

            if ("FIXED".equalsIgnoreCase(
                    coupon.getDiscountType())) {

                couponDiscount =
                        coupon.getDiscountValue();

            } else if ("PERCENTAGE".equalsIgnoreCase(
                    coupon.getDiscountType())) {

                couponDiscount =
                        totalAmount
                        * coupon.getDiscountValue()
                        / 100;
            }

            if (coupon.getMaximumDiscount() > 0) {

                couponDiscount = Math.min(
                        couponDiscount,
                        coupon.getMaximumDiscount());
            }

            couponDiscount = Math.min(
        couponDiscount,
        Math.max(0, totalAmount - freeTicketDiscount));
        }

        // -----------------------------
        // REWARDS
        // -----------------------------

        int silverRewards = request.getSilverRewards();
        boolean goldReward = request.isGoldReward();

        if (silverRewards < 0 || silverRewards > 2) {

            throw new BookingException(
                    "Silver rewards must be 0, 1, or 2");
        }

        // Cannot combine Silver and Gold
        if (goldReward && silverRewards > 0) {

            throw new BookingException(
                    "Silver and Gold rewards cannot be combined");
        }

        RewardsWallet wallet =
                rewardsService.getWallet(user.getId());

        double rewardDiscount = 0;

        // Silver discount
        if (silverRewards == 1) {

            if (wallet.getSilver() < 1) {
                throw new BookingException(
                        "Not enough silver rewards");
            }

            rewardDiscount = 20;

        } else if (silverRewards == 2) {

            if (wallet.getSilver() < 2) {
                throw new BookingException(
                        "Not enough silver rewards");
            }

            rewardDiscount = 50;
        }

        // Gold = 50% off
        if (goldReward) {

            if (wallet.getGold() < 1) {

                throw new BookingException(
                        "No gold reward available");
            }
rewardDiscount =
        (totalAmount - freeTicketDiscount - couponDiscount) * 0.50;
        }

        // Never allow negative amount
       rewardDiscount = Math.min(
        rewardDiscount,
        Math.max(0, totalAmount - freeTicketDiscount - couponDiscount));

      double finalAmount =
        totalAmount
        - freeTicketDiscount
        - couponDiscount
        - rewardDiscount;

finalAmount = Math.max(finalAmount, 0);

        // -----------------------------
        // CREATE BOOKING
        // -----------------------------

      

Booking booking = new Booking();

booking.setUser(user);
booking.setShow(show);

// Save the price breakdown
booking.setOriginalAmount(totalAmount);
booking.setFreeTicketDiscount(freeTicketDiscount);
booking.setRewardDiscount(rewardDiscount);
booking.setCouponDiscount(couponDiscount);

// Save the final amount
booking.setTotalAmount(finalAmount);
booking.setFreeTickets(freeTickets);

booking.setStatus("CONFIRMED");
booking.setPaymentStatus("PENDING");
booking.setBookingTime(LocalDateTime.now());

        Booking savedBooking =
                bookingRepository.save(booking);

        // -----------------------------
        // BOOK SEATS
        // -----------------------------

        for (ShowSeat showSeat : selectedSeats) {

            showSeat.setStatus("BOOKED");
            showSeatRepository.save(showSeat);

            BookingSeat bookingSeat =
                    new BookingSeat();

            bookingSeat.setBooking(savedBooking);
            bookingSeat.setShowSeat(showSeat);
if (freeSeats.stream().anyMatch(
        freeSeat -> freeSeat.getId().equals(showSeat.getId()))) {
    bookingSeat.setPrice(0.0);
} else {
    bookingSeat.setPrice(
        showSeat.getPrice() != null
            ? showSeat.getPrice()
            : showSeat.getSeat().getPrice()
    );
}
            bookingSeatRepository.save(bookingSeat);
        }

        // -----------------------------
        // REDEEM REWARDS
        // -----------------------------

        if (silverRewards > 0) {

            rewardsService.redeemSilver(
                    user.getId(),
                    silverRewards);
        }

        if (goldReward) {

            rewardsService.redeemGold(
                    user.getId());
        }
// AWARD REWARDS AFTER BOOKING
rewardsService.earnBronze(user.getId());
rewardsService.convertRewards(user.getId());
        return savedBooking;
    }

    public List<Booking> getMyBookings() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        return bookingRepository.findByUserId(
                user.getId());
    }

    public Booking getBookingById(Long bookingId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new BookingException(
                                        "Booking not found"));

        if (!booking.getUser().getId()
                .equals(user.getId())) {

            throw new BookingException(
                    "You are not allowed to view this booking");
        }

        return booking;
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new BookingException(
                                        "Booking not found"));

        if (!booking.getUser().getId()
                .equals(user.getId())) {

            throw new BookingException(
                    "You are not allowed to cancel this booking");
        }

        if ("CANCELLED".equalsIgnoreCase(
                booking.getStatus())) {

            throw new BookingException(
                    "Booking is already cancelled");
        }

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBookingId(
                        bookingId);

        for (BookingSeat bookingSeat : bookingSeats) {

            ShowSeat showSeat =
                    bookingSeat.getShowSeat();

            showSeat.setStatus("AVAILABLE");
            showSeatRepository.save(showSeat);
        }

        booking.setStatus("CANCELLED");

        return bookingRepository.save(booking);
    }
}