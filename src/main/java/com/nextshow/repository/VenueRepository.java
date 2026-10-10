package com.nextshow.repository;

import com.nextshow.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByCityIgnoreCase(String city);

    List<Venue> findByStateIgnoreCase(String state);
}