package com.nextshow.controller;

import com.nextshow.entity.Coupon;
import com.nextshow.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
            @RequestBody Coupon coupon) {

        return ResponseEntity.ok(
                couponService.createCoupon(coupon)
        );
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {

        return ResponseEntity.ok(
                couponService.getAllCoupons()
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCouponByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                couponService.getCouponByCode(code)
        );
    }
}
