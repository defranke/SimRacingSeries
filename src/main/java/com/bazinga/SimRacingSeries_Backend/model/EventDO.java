package com.bazinga.SimRacingSeries_Backend.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class EventDO {

    public enum EventType {
        QUALIFYING,
        RACE
    }

    @Id
    private String id;
    private String raceId;
    private String seriesId;
    private EventType type;
    private String title;
    private String description; // #Laps, Time-Limit, Time, Weather, ...
    private List<ResultDO> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResultDO> getResults() {
        return results;
    }

    public void setResults(List<ResultDO> results) {
        this.results = results;
    }
}