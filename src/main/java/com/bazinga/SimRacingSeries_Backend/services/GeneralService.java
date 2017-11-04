package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.CompleteSeriesTO;
import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/general")
public class GeneralService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private TeamRepository teamRepository;

    @RequestMapping(method = RequestMethod.GET, path = "completeSeries")
    public CompleteSeriesTO getCompleteSeries(@RequestParam String slugName) {
        SeriesDO series = seriesRepository.findBySlugNameIgnoreCase(slugName);
        if (series == null) {
            throw new IllegalArgumentException("SeriesNotFound");
        }
        List<TeamDO> teams = teamRepository.findBySeriesId(series.getId());

        CompleteSeriesTO completeSeries = new CompleteSeriesTO();
        completeSeries.setSeries(series);
        completeSeries.setTeams(teams);
        return completeSeries;
    }
}
