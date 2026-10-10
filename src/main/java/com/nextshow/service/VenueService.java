
package com.nextshow.service;

import com.nextshow.entity.Venue;
import com.nextshow.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public List<Venue> getVenuesByCity(String city) {
        return venueRepository.findByCityIgnoreCase(city);
    }

    public List<Venue> getVenuesByState(String state) {
        return venueRepository.findByStateIgnoreCase(state);
    }

    public Venue updateVenue(Long venueId, Venue updatedVenue) {

        Venue existingVenue = venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new RuntimeException("Venue not found"));

        existingVenue.setName(updatedVenue.getName());
        existingVenue.setAddress(updatedVenue.getAddress());
        existingVenue.setCity(updatedVenue.getCity());
        existingVenue.setState(updatedVenue.getState());
        existingVenue.setVenueType(updatedVenue.getVenueType());

        return venueRepository.save(existingVenue);
    }

    public void deleteVenue(Long venueId) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new RuntimeException("Venue not found"));

        venueRepository.delete(venue);
    }
}