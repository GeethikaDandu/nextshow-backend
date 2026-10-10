package com.nextshow.service;

import com.nextshow.entity.Event;
import com.nextshow.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // =========================
    // CREATE EVENT
    // =========================

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // =========================
    // GET ALL EVENTS
    // =========================

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // =========================
    // GET EVENTS BY CATEGORY
    // =========================

    public List<Event> getEventsByCategory(Long categoryId) {
        return eventRepository.findByCategoryId(categoryId);
    }

    // =========================
    // SEARCH EVENTS
    // =========================

    public List<Event> searchEvents(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // =========================
    // GET EVENTS BY DATE
    // =========================

    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepository.findByEventDate(date);
    }

    // =========================
    // GET EVENTS BY LANGUAGE
    // =========================

    public List<Event> getEventsByLanguage(String language) {
        return eventRepository.findByLanguageIgnoreCase(language);
    }

    // =========================
    // GET EVENTS BY PRICE
    // =========================

    public List<Event> getEventsByPriceRange(
            double minPrice,
            double maxPrice) {

        return eventRepository.findEventsByPriceRange(
                minPrice,
                maxPrice
        );
    }

    // =========================
    // GET EVENTS BY CITY
    // =========================

    public List<Event> getEventsByCity(String city) {
        return eventRepository.findEventsByCity(city);
    }

    // =========================
    // TOP RATED EVENTS
    // =========================

    public List<Event> getTopRatedEvents() {
        return eventRepository.findAllByOrderByRatingDesc();
    }

    // =========================
    // LOWEST PRICE EVENTS
    // =========================

    public List<Event> getEventsByLowestPrice() {
        return eventRepository.findAllByLowestPrice();
    }

    // =========================
    // UPCOMING EVENTS
    // =========================

    public List<Event> getUpcomingEvents() {
        return eventRepository.findAllByOrderByEventDateAsc();
    }

    // =========================
    // FILTER EVENTS
    // =========================

    public List<Event> filterEvents(
            String keyword,
            Long categoryId,
            String language,
            LocalDate date,
            Double minPrice,
            Double maxPrice) {

        List<Event> events = eventRepository.findAll();

        return events.stream()

                .filter(event ->
                        keyword == null ||
                        event.getTitle()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))

                .filter(event ->
                        categoryId == null ||
                        event.getCategory()
                                .getId()
                                .equals(categoryId))

                .filter(event ->
                        language == null ||
                        event.getLanguage()
                                .equalsIgnoreCase(language))

                .filter(event ->
                        date == null ||
                        event.getEventDate()
                                .equals(date))

                .filter(event -> {

                    if (minPrice == null && maxPrice == null) {
                        return true;
                    }

                    double minimum =
                            minPrice != null ? minPrice : 0;

                    double maximum =
                            maxPrice != null
                                    ? maxPrice
                                    : Double.MAX_VALUE;

                    return eventRepository
                            .findEventsByPriceRange(
                                    minimum,
                                    maximum
                            )
                            .stream()
                            .anyMatch(e ->
                                    e.getId()
                                            .equals(event.getId()));
                })

                .collect(Collectors.toList());
    }

    // =========================
    // UPDATE EVENT - ADMIN
    // =========================

    public Event updateEvent(
            Long eventId,
            Event updatedEvent) {

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        existingEvent.setTitle(
                updatedEvent.getTitle()
        );

        existingEvent.setDescription(
                updatedEvent.getDescription()
        );

        existingEvent.setImageUrl(
                updatedEvent.getImageUrl()
        );

        existingEvent.setEventDate(
                updatedEvent.getEventDate()
        );

        existingEvent.setLanguage(
                updatedEvent.getLanguage()
        );

        existingEvent.setAgeRating(
                updatedEvent.getAgeRating()
        );

        existingEvent.setRating(
                updatedEvent.getRating()
        );

        existingEvent.setCategory(
                updatedEvent.getCategory()
        );

        return eventRepository.save(existingEvent);
    }

    // =========================
    // DELETE EVENT - ADMIN
    // =========================

    public void deleteEvent(Long eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Event not found");
        }

        eventRepository.deleteById(eventId);
    }
}