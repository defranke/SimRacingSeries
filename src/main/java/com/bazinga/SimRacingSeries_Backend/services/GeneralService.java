package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.CompleteSeriesTO;
import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
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

    @Autowired
    public GeneralService(SeriesRepository seriesRepository, TeamRepository teamRepository, DriverRepository driverRepository) {
        this.seriesRepository = seriesRepository;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "completeSeries")
    public CompleteSeriesTO getCompleteSeries(@RequestParam String slugName) {
        SeriesDO series = seriesRepository.findBySlugNameIgnoreCase(slugName);
        if (series == null) {
            throw new IllegalArgumentException("SeriesNotFound");
        }
        List<TeamDO> teams = teamRepository.findBySeriesId(series.getId());
        List<DriverDO> drivers = driverRepository.findBySeriesId(series.getId());

        CompleteSeriesTO completeSeries = new CompleteSeriesTO();
        completeSeries.setSeries(series);
        completeSeries.setTeams(teams);
        completeSeries.setDrivers(drivers);
        return completeSeries;
    }


    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.GET, path = "authenticate/{seriesId}")
    public boolean authenticate(@PathVariable String seriesId) {
        return true;
    }

}
