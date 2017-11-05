package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teams")
public class TeamService {

    private TeamRepository teamRepository;

    private DriverService driverService;

    @Autowired
    public TeamService(TeamRepository teamRepository, DriverService driverService) {
        this.teamRepository = teamRepository;
        this.driverService = driverService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<TeamDO> getTeamsFor(@RequestParam(value = "seriesId") String seriesId) {
        return teamRepository.findBySeriesId(seriesId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody
    boolean deleteTeam(@RequestParam(value = "teamId") String teamId) {
        if(teamId != null && !teamId.isEmpty()) {
            teamRepository.delete(teamId);
            driverService.deleteDriversOfTeam(teamId);
            return true;
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    TeamDO putTeam(@RequestBody TeamDO team) {
        if (team.getId() != null) {
            throw new IllegalArgumentException("TeamAlreadySaved");
        }
        if (team.getSeriesId() == null || team.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("SeriesIsMissing");
        }
        if (team.getName().isEmpty()) {
            throw new IllegalArgumentException("TeamNameIsMissing");
        }
        return teamRepository.insert(team);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    TeamDO postTeam(@RequestBody TeamDO team) {
        if (team.getId() == null || team.getId().isEmpty()) {
            throw new IllegalArgumentException("TeamNotSavedYet");
        }
        if (team.getSeriesId() == null || team.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("SeriesIsMissing");
        }
        if (team.getName().isEmpty()) {
            throw new IllegalArgumentException("TeamNameIsMissing");
        }
        return teamRepository.save(team);
    }
}
