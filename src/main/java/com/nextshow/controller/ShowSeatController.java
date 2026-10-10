package com.nextshow.controller;

import com.nextshow.entity.ShowSeat;
import com.nextshow.service.ShowSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @PostMapping
    public ResponseEntity<ShowSeat> addShowSeat(@RequestBody ShowSeat showSeat) {
        return ResponseEntity.ok(showSeatService.addShowSeat(showSeat));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowSeat>> getSeatsByShow(
            @PathVariable Long showId) {
        return ResponseEntity.ok(showSeatService.getSeatsByShow(showId));
    }

    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<ShowSeat>> getAvailableSeats(
            @PathVariable Long showId) {
        return ResponseEntity.ok(showSeatService.getAvailableSeats(showId));
    }

    @PostMapping("/generate/{showId}/{screenId}")
    public ResponseEntity<String> generateSeats(
            @PathVariable Long showId,
            @PathVariable Long screenId) {

        showSeatService.createSeatsForShow(showId, screenId);

        return ResponseEntity.ok("Show seats generated successfully");
    }
        @PostMapping("/generate-all")
    public ResponseEntity<String> generateSeatsForAllShows() {
        showSeatService.createSeatsForAllShows();
        return ResponseEntity.ok(
            "Seats generated for all shows successfully"
        );
    }
}