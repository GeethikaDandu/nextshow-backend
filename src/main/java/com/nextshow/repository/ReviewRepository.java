package com.nextshow.repository;

import com.nextshow.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByEventId(Long eventId);

    List<Review> findByUserId(Long userId);
}