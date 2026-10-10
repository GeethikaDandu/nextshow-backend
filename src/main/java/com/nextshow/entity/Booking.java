package com.nextshow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalAmount;
private int freeTickets;
private double originalAmount;
private double freeTicketDiscount;
private double rewardDiscount;
private double couponDiscount;
    private String status;

    private String paymentStatus;

    private LocalDateTime bookingTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
    public int getFreeTickets() {
    return freeTickets;
}

public void setFreeTickets(int freeTickets) {
    this.freeTickets = freeTickets;
}
public double getOriginalAmount() {
    return originalAmount;
}

public void setOriginalAmount(double originalAmount) {
    this.originalAmount = originalAmount;
}

public double getFreeTicketDiscount() {
    return freeTicketDiscount;
}

public void setFreeTicketDiscount(double freeTicketDiscount) {
    this.freeTicketDiscount = freeTicketDiscount;
}

public double getRewardDiscount() {
    return rewardDiscount;
}

public void setRewardDiscount(double rewardDiscount) {
    this.rewardDiscount = rewardDiscount;
}

public double getCouponDiscount() {
    return couponDiscount;
}

public void setCouponDiscount(double couponDiscount) {
    this.couponDiscount = couponDiscount;
}
}