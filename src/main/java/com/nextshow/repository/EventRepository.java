package com.nextshow.repository;

import com.nextshow.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Category filter
    List<Event> findByCategoryId(Long categoryId);

    // Search by title
    List<Event> findByTitleContainingIgnoreCase(String keyword);

    // Date filter
    List<Event> findByEventDate(LocalDate eventDate);

    // Language filter
    List<Event> findByLanguageIgnoreCase(String language);

    // Top rated events
    List<Event> findAllByOrderByRatingDesc();
    List<Event> findAllByOrderByEventDateAsc();

    // Price range filter
    @Query("""
        SELECT DISTINCT e
        FROM Event e
        JOIN Show s ON s.event.id = e.id
        JOIN Seat seat ON seat.screen.id = s.screen.id
        WHERE seat.price BETWEEN :minPrice AND :maxPrice
        """)
    List<Event> findEventsByPriceRange(
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );

    // Events by city
    @Query("""
        SELECT DISTINCT e
        FROM Event e
        JOIN Show s ON s.event.id = e.id
        JOIN Screen screen ON screen.id = s.screen.id
        JOIN Venue v ON v.id = screen.venue.id
        WHERE LOWER(v.city) = LOWER(:city)
        """)
    List<Event> findEventsByCity(
            @Param("city") String city
    );

    // Lowest price first
    @Query("""
        SELECT DISTINCT e
        FROM Event e
        JOIN Show s ON s.event.id = e.id
        JOIN Seat seat ON seat.screen.id = s.screen.id
        GROUP BY e
        ORDER BY MIN(seat.price) ASC
        """)
    List<Event> findAllByLowestPrice();
}