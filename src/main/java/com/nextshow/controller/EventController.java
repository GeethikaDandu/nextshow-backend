package com.nextshow.controller;

import com.nextshow.entity.Event;
import com.nextshow.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create event
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.createEvent(event)
        );
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents()
        );
    }

    // Filter by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Event>> getEventsByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                eventService.getEventsByCategory(categoryId)
        );
    }

    // Search by title
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                eventService.searchEvents(keyword)
        );
    }

    // Filter by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Event>> getEventsByDate(
            @PathVariable LocalDate date) {

        return ResponseEntity.ok(
                eventService.getEventsByDate(date)
        );
    }

    // Filter by language
    @GetMapping("/language/{language}")
    public ResponseEntity<List<Event>> getEventsByLanguage(
            @PathVariable String language) {

        return ResponseEntity.ok(
                eventService.getEventsByLanguage(language)
        );
    }

    // Filter by price
    @GetMapping("/price")
    public ResponseEntity<List<Event>> getEventsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {

        return ResponseEntity.ok(
                eventService.getEventsByPriceRange(
                        minPrice,
                        maxPrice
                )
        );
    }
@GetMapping("/city/{city}")
public ResponseEntity<List<Event>> getEventsByCity(
        @PathVariable String city) {

    return ResponseEntity.ok(
            eventService.getEventsByCity(city)
    );
}
@GetMapping("/sort/rating")
public ResponseEntity<List<Event>> getTopRatedEvents() {

    return ResponseEntity.ok(
            eventService.getTopRatedEvents()
    );
}
@GetMapping("/sort/price")
public ResponseEntity<List<Event>> getEventsByLowestPrice() {

    return ResponseEntity.ok(
            eventService.getEventsByLowestPrice()
    );
}
@GetMapping("/sort/upcoming")
public ResponseEntity<List<Event>> getUpcomingEvents() {

    return ResponseEntity.ok(
            eventService.getUpcomingEvents()
    );
}
    // Combined filter
    @GetMapping("/filter")
    public ResponseEntity<List<Event>> filterEvents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        return ResponseEntity.ok(
                eventService.filterEvents(
                        keyword,
                        categoryId,
                        language,
                        date,
                        minPrice,
                        maxPrice
                )
        );
    }
}