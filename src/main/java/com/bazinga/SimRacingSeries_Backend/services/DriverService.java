package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{seriesId}")
    public @ResponseBody
    List<DriverDO> getDriverFor(@PathVariable String seriesId) {
        return driverRepository.findBySeriesId(seriesId);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.PUT, path = "/{seriesId}")
    public @ResponseBody
    DriverDO putDriver(@PathVariable String seriesId, @RequestBody DriverDO driver) {
        if(!seriesId.equals(driver.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
        if (driver.getId() != null && !driver.getId().isEmpty()) {
            throw new IllegalArgumentException("DriverAlreadyCreated");
        }
        if (driver.getSeriesId() == null || driver.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("DriverSeriesIsMissing");
        }
        if (driver.getTeamId() == null || driver.getTeamId().isEmpty()) {
            throw new IllegalArgumentException("DriverTeamIsMissing");
        }
        if (driver.getName() == null || driver.getName().isEmpty()) {
            throw new IllegalArgumentException("DriverNameIsMissing");
        }
        return driverRepository.insert(driver);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.POST, path = "/{seriesId}")
    public @ResponseBody
    DriverDO postDriver(@PathVariable String seriesId, @RequestBody DriverDO driver) {
        if(!seriesId.equals(driver.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
        if (driver.getId() == null || driver.getId().isEmpty()) {
            throw new IllegalArgumentException("DriverNotCreatedYet");
        }
        if (driver.getSeriesId() == null || driver.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("DriverSeriesIsMissing");
        }
        if (driver.getTeamId() == null || driver.getTeamId().isEmpty()) {
            throw new IllegalArgumentException("DriverTeamIsMissing");
        }
        if (driver.getName() == null || driver.getName().isEmpty()) {
            throw new IllegalArgumentException("DriverNameIsMissing");
        }
        return driverRepository.save(driver);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{seriesId}")
    public @ResponseBody
    boolean deleteDriver(@PathVariable String seriesId, @RequestParam(value = "driverId") String driverId) {
        if (driverId != null && !driverId.isEmpty()) {
            driverRepository.delete(driverId);
            return true;
        }
        return false;
    }

    public void deleteDriversOfTeam(String teamId) {
        if (teamId != null) {
            List<DriverDO> driversInTeam = driverRepository.findByTeamId(teamId);
            if (driversInTeam != null) {
                for (DriverDO driver : driversInTeam) {
                    driverRepository.delete(driver.getId());
                }
            }
        }
    }
}
