package com.nextshow.controller;

import com.nextshow.entity.Screen;
import com.nextshow.service.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<Screen> addScreen(@RequestBody Screen screen) {

        Screen savedScreen = screenService.addScreen(screen);

        return ResponseEntity.ok(savedScreen);
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens() {

        return ResponseEntity.ok(screenService.getAllScreens());
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<Screen>> getScreensByVenue(
            @PathVariable Long venueId) {

        return ResponseEntity.ok(
                screenService.getScreensByVenue(venueId)
        );
    }
}