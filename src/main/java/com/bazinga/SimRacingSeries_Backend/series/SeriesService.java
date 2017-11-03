package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/series")
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    SeriesDO putSeries(@RequestBody SeriesDO series) {
        if(series.getName().isEmpty()) {
            throw new IllegalArgumentException("NameIsEmpty");
        }
        if(series.getPassword().isEmpty()) {
            throw new IllegalArgumentException("PasswordIsEmpty");
        }
        if(!series.getSlugName().matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("InvalidSlugName");
        }
        if(seriesRepository.findBySlugName(series.getSlugName()) != null) {
            throw new IllegalArgumentException("SlugAlreadyUsed");
        }
        return seriesRepository.insert(series);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    SeriesDO postSeries(@RequestBody SeriesDO series) {
        if(series.getId() == null) {
            throw new IllegalArgumentException("SeriesNotCreatedYet");
        }
        if(series.getName().isEmpty()) {
            throw new IllegalArgumentException("NameIsEmpty");
        }
        if(series.getPassword().isEmpty()) {
            throw new IllegalArgumentException("PasswordIsEmpty");
        }
        if(!series.getSlugName().matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("InvalidSlugName");
        }
        SeriesDO existingSeries = seriesRepository.findBySlugName(series.getSlugName());
        if(existingSeries != null && !existingSeries.getId().equals(series.getId())) {
            throw new IllegalArgumentException("SlugAlreadyUsed");
        }
        return seriesRepository.save(series);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    SeriesDO getSeries(@RequestParam(value = "slugName") String slugName) {
        return seriesRepository.findBySlugName(slugName);
    }
}
