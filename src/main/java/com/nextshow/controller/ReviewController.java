package com.nextshow.controller;

import com.nextshow.entity.Review;
import com.nextshow.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<Review> addReview(
            @PathVariable Long eventId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment) {

        return ResponseEntity.ok(
                reviewService.addReview(
                        eventId,
                        rating,
                        comment
                )
        );
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Review>> getReviewsByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByEvent(eventId)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<Review>> getMyReviews() {

        return ResponseEntity.ok(
                reviewService.getMyReviews()
        );
    }
}
