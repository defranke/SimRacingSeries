package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<DriverDO> getDriverFor(@RequestParam(value = "seriesId") String seriesId) {
        return driverRepository.findBySeriesId(seriesId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    DriverDO putDriver(@RequestBody DriverDO driver) {
        if(driver.getId() != null && !driver.getId().isEmpty()) {
            throw new IllegalArgumentException("DriverAlreadyCreated");
        }
        if(driver.getSeriesId() == null || driver.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("DriverSeriesIsMissing");
        }
        if(driver.getTeamId() == null || driver.getTeamId().isEmpty()) {
            throw new IllegalArgumentException("DriverTeamIsMissing");
        }
        if(driver.getName() == null || driver.getName().isEmpty()) {
            throw new IllegalArgumentException("DriverNameIsMissing");
        }
        return driverRepository.insert(driver);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    DriverDO postDriver(@RequestBody DriverDO driver) {
        if(driver.getId() == null || driver.getId().isEmpty()) {
            throw new IllegalArgumentException("DriverNotCreatedYet");
        }
        if(driver.getSeriesId() == null || driver.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("DriverSeriesIsMissing");
        }
        if(driver.getTeamId() == null || driver.getTeamId().isEmpty()) {
            throw new IllegalArgumentException("DriverTeamIsMissing");
        }
        if(driver.getName() == null || driver.getName().isEmpty()) {
            throw new IllegalArgumentException("DriverNameIsMissing");
        }
        return driverRepository.save(driver);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody
    boolean deleteDriver(@RequestParam(value = "driverId") String driverId) {
        if(driverId != null && !driverId.isEmpty()) {
            driverRepository.delete(driverId);
            return true;
        }
        return false;
    }

    public void deleteDriversOfTeam(String teamId) {
        if (teamId != null) {
            List<DriverDO> driversInTeam = driverRepository.findByTeamId(teamId);
            if(driversInTeam != null) {
                for (DriverDO driver : driversInTeam) {
                    driverRepository.delete(driver.getId());
                }
            }
        }
    }
}
