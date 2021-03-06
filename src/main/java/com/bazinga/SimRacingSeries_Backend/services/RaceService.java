package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.RaceDO;
import com.bazinga.SimRacingSeries_Backend.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/race")
public class RaceService {

    private RaceRepository raceRepository;

    @Autowired
    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{seriesId}")
    public @ResponseBody
    List<RaceDO> getRacesFor(@PathVariable String seriesId) {
        return raceRepository.findBySeriesId(seriesId);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.PUT, path = "/{seriesId}")
    public RaceDO putRace(@PathVariable String seriesId, @RequestBody RaceDO input) {
        if(!seriesId.equals(input.getSeriesId())) {
            throw new IllegalArgumentException("RaceSeriesIdNotMatching");
        }
        if (input.getId() != null && !input.getId().isEmpty()) {
            throw new IllegalArgumentException("RaceAlreadySaved");
        }
        if (input.getSeriesId() == null || input.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("RaceSeriesIsMissing");
        }
        if (input.getTrack().isEmpty()) {
            throw new IllegalArgumentException("RaceTrackIsMissing");
        }
        return raceRepository.insert(input);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.POST, path = "/{seriesId}")
    public RaceDO postRace(@PathVariable String seriesId, @RequestBody RaceDO input) {
        if(!seriesId.equals(input.getSeriesId())) {
            throw new IllegalArgumentException("RaceSeriesIdNotMatching");
        }
        if (input.getId() == null) {
            throw new IllegalArgumentException("RaceNotYetCreated");
        }
        if (input.getSeriesId() == null || input.getSeriesId().isEmpty()) {
            throw new IllegalArgumentException("RaceSeriesIsMissing");
        }
        if (input.getTrack().isEmpty()) {
            throw new IllegalArgumentException("RaceTrackIsMissing");
        }
        return raceRepository.save(input);
    }

    @PreAuthorize("hasAuthority(#seriesId)")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{seriesId}")
    public boolean deleteRace(@PathVariable  String seriesId, @RequestParam String raceId) {
        if(raceId != null && !raceId.isEmpty()) {
            raceRepository.delete(raceId);
            return true;
        }
        return false;
    }
}
