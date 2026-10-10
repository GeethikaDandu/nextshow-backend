package com.nextshow.repository;

import com.nextshow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByEventId(Long eventId);

    List<Show> findByScreenId(Long screenId);
}