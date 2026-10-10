package com.nextshow.service;

import com.nextshow.entity.Screen;
import com.nextshow.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    public Screen addScreen(Screen screen) {
        return screenRepository.save(screen);
    }

    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    public List<Screen> getScreensByVenue(Long venueId) {
        return screenRepository.findByVenueId(venueId);
    }
}
