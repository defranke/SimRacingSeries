package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series")
public class SeriesService {

    private SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    SeriesDO putSeries(@RequestBody SeriesDO series) {
        if(series.getId() != null) {
            throw new IllegalArgumentException("SeriesAlreadyExists");
        }
        if (series.getName().isEmpty()) {
            throw new IllegalArgumentException("NameIsEmpty");
        }
        if (series.getPassword().isEmpty()) {
            throw new IllegalArgumentException("PasswordIsEmpty");
        }
        if (!series.getSlugName().matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("InvalidSlugName");
        }
        if (seriesRepository.findBySlugNameIgnoreCase(series.getSlugName()) != null) {
            throw new IllegalArgumentException("SlugAlreadyUsed");
        }
        return seriesRepository.insert(series);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.POST, path = "/{seriesId}")
    public @ResponseBody
    SeriesDO postSeries(@PathVariable String seriesId, @RequestBody SeriesDO series) {
        if(!seriesId.equals(series.getId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
        if (series.getId() == null || series.getId().isEmpty()) {
            throw new IllegalArgumentException("SeriesNotCreatedYet");
        }
        if (series.getName().isEmpty()) {
            throw new IllegalArgumentException("NameIsEmpty");
        }
        if (series.getPassword().isEmpty()) {
            throw new IllegalArgumentException("PasswordIsEmpty");
        }
        if (!series.getSlugName().matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("InvalidSlugName");
        }
        SeriesDO existingSeries = seriesRepository.findBySlugNameIgnoreCase(series.getSlugName());
        if (existingSeries != null && !existingSeries.getId().equals(series.getId())) {
            throw new IllegalArgumentException("SlugAlreadyUsed");
        }
        return seriesRepository.save(series);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{slugName}")
    public @ResponseBody
    SeriesDO getSeries(@PathVariable String slugName) {
        return seriesRepository.findBySlugNameIgnoreCase(slugName);
    }
}
