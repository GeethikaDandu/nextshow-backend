
package com.nextshow.controller;

import com.nextshow.entity.Seat;
import com.nextshow.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) {
        Seat savedSeat = seatService.addSeat(seat);
        return ResponseEntity.ok(savedSeat);
    }

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatsByScreen(
            @PathVariable Long screenId) {
        return ResponseEntity.ok(
                seatService.getSeatsByScreen(screenId)
        );
    }

    @PostMapping("/generate/{screenId}")
    public ResponseEntity<List<Seat>> generateSeats(
            @PathVariable Long screenId) {
        return ResponseEntity.ok(
                seatService.generateSeatsForScreen(screenId)
        );
    }
}

