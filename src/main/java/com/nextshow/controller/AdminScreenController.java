
package com.nextshow.controller;

import com.nextshow.entity.Screen;
import com.nextshow.repository.ScreenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/screens")
public class AdminScreenController {

    private final ScreenRepository screenRepository;

    public AdminScreenController(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    @PostMapping
    public ResponseEntity<Screen> createScreen(
            @RequestBody Screen screen) {

        return ResponseEntity.ok(
                screenRepository.save(screen)
        );
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens() {
        return ResponseEntity.ok(
                screenRepository.findAll()
        );
    }

    @PutMapping("/{screenId}")
    public ResponseEntity<Screen> updateScreen(
            @PathVariable Long screenId,
            @RequestBody Screen updatedScreen) {

        Screen existingScreen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new RuntimeException("Screen not found"));

        existingScreen.setName(updatedScreen.getName());
        existingScreen.setTotalSeats(updatedScreen.getTotalSeats());
        existingScreen.setVenue(updatedScreen.getVenue());

        return ResponseEntity.ok(
                screenRepository.save(existingScreen)
        );
    }

    @DeleteMapping("/{screenId}")
    public ResponseEntity<String> deleteScreen(
            @PathVariable Long screenId) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new RuntimeException("Screen not found"));

        screenRepository.delete(screen);

        return ResponseEntity.ok("Screen deleted successfully");
    }
}