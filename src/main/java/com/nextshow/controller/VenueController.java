package com.nextshow.controller;

import com.nextshow.entity.Venue;
import com.nextshow.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(
            @RequestBody Venue venue) {

        return ResponseEntity.ok(
                venueService.createVenue(venue)
        );
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {

        return ResponseEntity.ok(
                venueService.getAllVenues()
        );
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Venue>> getVenuesByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                venueService.getVenuesByCity(city)
        );
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Venue>> getVenuesByState(
            @PathVariable String state) {

        return ResponseEntity.ok(
                venueService.getVenuesByState(state)
        );
    }
}