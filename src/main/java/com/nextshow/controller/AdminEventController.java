package com.nextshow.controller;

import com.nextshow.entity.Event;
import com.nextshow.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.createEvent(event)
        );
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents()
        );
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.updateEvent(eventId, event)
        );
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(
            @PathVariable Long eventId) {

        eventService.deleteEvent(eventId);

        return ResponseEntity.ok(
                "Event deleted successfully"
        );
    }
}