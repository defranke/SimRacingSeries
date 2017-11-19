package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.EventDO;
import com.bazinga.SimRacingSeries_Backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/{raceId}")
    public @ResponseBody
    List<EventDO> getEventsFor(@PathVariable String raceId) {
        return eventRepository.findByRaceId(raceId);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @PutMapping("/{seriesId}")
    public @ResponseBody
    EventDO putEvent(@PathVariable String seriesId, @RequestBody EventDO event) {
        if (!seriesId.equals(event.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
        if (event.getId() != null && !event.getId().isEmpty()) {
            throw new IllegalArgumentException("EventAlreadyCreated");
        }
        if (event.getSeriesId() == null || event.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("EventSeriesIsMissing");
        }
        if(event.getRaceId() == null || event.getRaceId().isEmpty()) {
            throw new IllegalArgumentException("EventRaceIdIsMissing");
        }
        if(event.getType() == null) {
            throw new IllegalArgumentException("EventTypeIsMissing");
        }
        if(event.getTitle() == null || event.getTitle().isEmpty()) {
            throw new IllegalArgumentException("EventTitleIsMissing");
        }
        return eventRepository.insert(event);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @PostMapping("/{seriesId}")
    public @ResponseBody
    EventDO postEvent(@PathVariable String seriesId, @RequestBody EventDO event) {
        if (!seriesId.equals(event.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
        if (event.getId() == null) {
            throw new IllegalArgumentException("EventNotYetCreated");
        }
        if (event.getSeriesId() == null || event.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("EventSeriesIsMissing");
        }
        if(event.getRaceId() == null || event.getRaceId().isEmpty()) {
            throw new IllegalArgumentException("EventRaceIdIsMissing");
        }
        if(event.getType() == null) {
            throw new IllegalArgumentException("EventTypeIsMissing");
        }
        if(event.getTitle() == null || event.getTitle().isEmpty()) {
            throw new IllegalArgumentException("EventTitleIsMissing");
        }
        return eventRepository.save(event);
    }
}
