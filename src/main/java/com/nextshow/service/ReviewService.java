package com.nextshow.service;

import com.nextshow.entity.Event;
import com.nextshow.entity.Review;
import com.nextshow.entity.User;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.EventRepository;
import com.nextshow.repository.ReviewRepository;
import com.nextshow.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            EventRepository eventRepository,
            UserRepository userRepository) {

        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Review addReview(
            Long eventId,
            int rating,
            String comment) {

        if (rating < 1 || rating > 5) {
            throw new BookingException(
                    "Rating must be between 1 and 5");
        }

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new BookingException("Event not found"));

        Review review = new Review();

        review.setUser(user);
        review.setEvent(event);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewTime(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByEvent(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }

    public List<Review> getMyReviews() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        return reviewRepository.findByUserId(user.getId());
    }
}
