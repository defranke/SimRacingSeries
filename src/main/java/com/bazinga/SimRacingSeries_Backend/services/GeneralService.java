package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.*;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import com.bazinga.SimRacingSeries_Backend.repository.RaceRepository;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general")
public class GeneralService {

    private SeriesRepository seriesRepository;
    private TeamRepository teamRepository;
    private DriverRepository driverRepository;
    private RaceRepository raceRepository;

    @Autowired
    public GeneralService(SeriesRepository seriesRepository, TeamRepository teamRepository,
                          DriverRepository driverRepository, RaceRepository raceRepository) {
        this.seriesRepository = seriesRepository;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
        this.raceRepository = raceRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "completeSeries")
    public CompleteSeriesTO getCompleteSeries(@RequestParam String slugName) {
        SeriesDO series = seriesRepository.findBySlugNameIgnoreCase(slugName);
        if (series == null) {
            throw new IllegalArgumentException("SeriesNotFound");
        }
        List<TeamDO> teams = teamRepository.findBySeriesId(series.getId());
        List<DriverDO> drivers = driverRepository.findBySeriesId(series.getId());
        List<RaceDO> races = raceRepository.findBySeriesId(series.getId());

        CompleteSeriesTO completeSeries = new CompleteSeriesTO();
        completeSeries.setSeries(series);
        completeSeries.setTeams(teams);
        completeSeries.setDrivers(drivers);
        completeSeries.setRaces(races);
        return completeSeries;
    }


    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.GET, path = "authenticate/{seriesId}")
    public boolean authenticate(@PathVariable String seriesId) {
        return true;
    }

}
