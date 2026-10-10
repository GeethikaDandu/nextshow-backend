
package com.nextshow.controller;

import com.nextshow.entity.Show;
import com.nextshow.repository.ShowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shows")
public class AdminShowController {

    private final ShowRepository showRepository;

    public AdminShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @PostMapping
    public ResponseEntity<Show> createShow(
            @RequestBody Show show) {

        return ResponseEntity.ok(
                showRepository.save(show)
        );
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(
                showRepository.findAll()
        );
    }

    @PutMapping("/{showId}")
    public ResponseEntity<Show> updateShow(
            @PathVariable Long showId,
            @RequestBody Show updatedShow) {

        Show existingShow = showRepository.findById(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        existingShow.setEvent(updatedShow.getEvent());
        existingShow.setScreen(updatedShow.getScreen());
        existingShow.setShowDate(updatedShow.getShowDate());
        existingShow.setShowTime(updatedShow.getShowTime());

        return ResponseEntity.ok(
                showRepository.save(existingShow)
        );
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<String> deleteShow(
            @PathVariable Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        showRepository.delete(show);

        return ResponseEntity.ok("Show deleted successfully");
    }
}
