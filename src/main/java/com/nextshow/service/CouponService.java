package com.nextshow.service;

import com.nextshow.entity.Coupon;
import com.nextshow.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponByCode(String code) {

        Coupon coupon = couponRepository
                .findByCodeIgnoreCase(code)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found"));

        if (!coupon.isActive()) {
            throw new RuntimeException("Coupon is inactive");
        }

        if (coupon.getExpiryDate() != null &&
                coupon.getExpiryDate().isBefore(LocalDate.now())) {

            throw new RuntimeException("Coupon has expired");
        }

        return coupon;
    }
}