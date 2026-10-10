package com.nextshow.service;

import com.nextshow.entity.Seat;
import com.nextshow.entity.Show;
import com.nextshow.entity.ShowSeat;
import com.nextshow.repository.SeatRepository;
import com.nextshow.repository.ShowRepository;
import com.nextshow.repository.ShowSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public ShowSeatService(
            ShowSeatRepository showSeatRepository,
            ShowRepository showRepository,
            SeatRepository seatRepository) {

        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public ShowSeat addShowSeat(ShowSeat showSeat) {
        return showSeatRepository.save(showSeat);
    }

    public List<ShowSeat> getSeatsByShow(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }

    public List<ShowSeat> getAvailableSeats(Long showId) {
        return showSeatRepository.findByShowIdAndStatus(
                showId, "AVAILABLE");
    }

    public void createSeatsForShow(Long showId, Long screenId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seats = seatRepository.findByScreenId(screenId);

        for (Seat seat : seats) {

    // Skip if this seat is already created for this show
    if (showSeatRepository.existsByShowIdAndSeatId(showId, seat.getId())) {
        continue;
    }

    ShowSeat showSeat = new ShowSeat();

    showSeat.setShow(show);
    showSeat.setSeat(seat);
   showSeat.setStatus("AVAILABLE");
showSeat.setSeatType(seat.getSeatType());
showSeat.setPrice(seat.getPrice());

showSeatRepository.save(showSeat);
}
    }

public void createSeatsForAllShows() {
    List<Show> shows = showRepository.findAll();

    for (Show show : shows) {
        createSeatsForShow(
            show.getId(),
            show.getScreen().getId()
        );
    }
}
}