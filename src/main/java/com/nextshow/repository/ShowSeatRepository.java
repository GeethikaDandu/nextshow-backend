package com.nextshow.repository;

import com.nextshow.entity.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long showId);

    List<ShowSeat> findByShowIdAndStatus(Long showId, String status);

    boolean existsByShowIdAndSeatId(Long showId, Long seatId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShowSeat> findWithLockById(Long id);
}
