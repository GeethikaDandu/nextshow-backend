
package com.nextshow.service;

import com.nextshow.entity.Seat;
import com.nextshow.entity.Screen;
import com.nextshow.repository.SeatRepository;
import com.nextshow.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(
            SeatRepository seatRepository,
            ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    public Seat addSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public List<Seat> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    public List<Seat> generateSeatsForScreen(Long screenId) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new RuntimeException("Screen not found"));

        if (!seatRepository.findByScreenId(screenId).isEmpty()) {
            throw new RuntimeException(
                    "Seats already exist for this screen");
        }

        List<Seat> seats = new ArrayList<>();

        int totalSeats = screen.getTotalSeats();
        int seatsPerRow = 10;
        int totalRows = (int) Math.ceil(
                (double) totalSeats / seatsPerRow);

        for (int row = 0; row < totalRows; row++) {
            char rowName = (char) ('A' + row);

            for (int number = 1;
                 number <= seatsPerRow && seats.size() < totalSeats;
                 number++) {

                Seat seat = new Seat();
                seat.setSeatNumber(
                        rowName + String.valueOf(number));
                seat.setSeatType("REGULAR");
                seat.setPrice(200.0);
                seat.setScreen(screen);

                seats.add(seat);
            }
        }

        return seatRepository.saveAll(seats);
    }
}

