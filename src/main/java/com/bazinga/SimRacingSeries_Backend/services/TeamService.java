package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @RequestMapping(method = RequestMethod.GET, path = "/{seriesId}")
    public @ResponseBody
    List<TeamDO> getTeamsFor(@PathVariable String seriesId) {
        return teamRepository.findBySeriesId(seriesId);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{seriesId}")
    public @ResponseBody
    boolean deleteTeam(@PathVariable String seriesId, @RequestParam(value = "teamId") String teamId) {
        if(teamId != null && !teamId.isEmpty()) {
            teamRepository.delete(teamId);
            driverService.deleteDriversOfTeam(teamId);
            return true;
        }
        return false;
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.PUT, path = "/{seriesId}")
    public @ResponseBody
    TeamDO putTeam(@PathVariable String seriesId, @RequestBody TeamDO team) {
        if(!seriesId.equals(team.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
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

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.POST, path = "/{seriesId}")
    public @ResponseBody
    TeamDO postTeam(@PathVariable String seriesId, @RequestBody TeamDO team) {
        if(!seriesId.equals(team.getSeriesId())) {
            throw new IllegalArgumentException("SeriesIdNotMatching");
        }
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
