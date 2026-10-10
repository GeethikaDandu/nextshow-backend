package com.nextshow.service;

import com.nextshow.entity.Show;
import com.nextshow.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show addShow(Show show) {
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByEvent(Long eventId) {
        return showRepository.findByEventId(eventId);
    }

    public List<Show> getShowsByScreen(Long screenId) {
        return showRepository.findByScreenId(screenId);
    }
}
