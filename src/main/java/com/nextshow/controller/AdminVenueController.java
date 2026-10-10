
package com.nextshow.controller;

import com.nextshow.entity.Venue;
import com.nextshow.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/venues")
public class AdminVenueController {

    private final VenueService venueService;

    public AdminVenueController(VenueService venueService) {
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

    @PutMapping("/{venueId}")
    public ResponseEntity<Venue> updateVenue(
            @PathVariable Long venueId,
            @RequestBody Venue venue) {

        return ResponseEntity.ok(
                venueService.updateVenue(venueId, venue)
        );
    }

    @DeleteMapping("/{venueId}")
    public ResponseEntity<String> deleteVenue(
            @PathVariable Long venueId) {

        venueService.deleteVenue(venueId);

        return ResponseEntity.ok(
                "Venue deleted successfully"
        );
    }
}
