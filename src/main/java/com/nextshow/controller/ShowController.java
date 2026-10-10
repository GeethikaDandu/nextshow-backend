package com.nextshow.controller;

import com.nextshow.entity.Show;
import com.nextshow.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<Show> addShow(@RequestBody Show show) {
        Show savedShow = showService.addShow(show);
        return ResponseEntity.ok(savedShow);
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Show>> getShowsByEvent(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(showService.getShowsByEvent(eventId));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Show>> getShowsByScreen(
            @PathVariable Long screenId) {
        return ResponseEntity.ok(showService.getShowsByScreen(screenId));
    }
}
