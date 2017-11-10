package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.RaceDO;
import com.bazinga.SimRacingSeries_Backend.repository.RaceRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RaceServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private RaceRepository raceRepository;

    private RaceService raceService;

    @Before
    public void setUp() throws Exception {
        raceRepository = mock(RaceRepository.class);
        raceService = new RaceService(raceRepository);
    }

    @Test
    public void testReadService() throws Exception {
        RaceDO race1 = createRace("1", "SeriesId", "Track", 123);
        RaceDO race2 = createRace("2", "SeriesId", "Track2", 456);
        doReturn(Arrays.asList(race1, race2)).when(raceRepository).findBySeriesId("SeriesId");

        List<RaceDO> races = raceService.getRacesFor("SeriesId");
        assertEquals(2, races.size());
        assertEquals("1", races.get(0).getId());
        assertEquals("Track", races.get(0).getTrack());
        assertEquals("2", races.get(1).getId());
        assertEquals("Track2", races.get(1).getTrack());
    }

    @Test
    public void testPutRace() throws Exception {
        RaceDO input = createRace(null, "SeriesId", "Track",123);
        doReturn(createRace("1", "SeriesId", "Track",123))
                .when(raceRepository).insert(any(RaceDO.class));

        RaceDO actual = raceService.putRace("SeriesId", input);

        assertNotNull(actual);
        assertEquals("1", actual.getId());
        assertEquals("Track", actual.getTrack());
        verify(raceRepository).insert(input);
    }

    @Test
    public void testPutTeamFailsWhenSeriesIdNotMatching() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceSeriesIdNotMatching");

        RaceDO input = createRace(null, "SeriesId", "Track",123);

        raceService.putRace("WrongSeriesId", input);
    }

    @Test
    public void testPutTeamFailsWhenSeriesIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceSeriesIsMissing");

        RaceDO input = createRace(null, "", "Track",123);

        raceService.putRace("", input);
    }

    @Test
    public void testPutTeamFailsWhenIdIsNotEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceAlreadySaved");

        RaceDO input = createRace("123", "SeriesId", "Track",123);

        raceService.putRace("SeriesId", input);
    }

    @Test
    public void testPutTeamFailsWhenTrackIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceTrackIsMissing");

        RaceDO input = createRace(null, "SeriesId", "",123);

        raceService.putRace("SeriesId", input);
    }

    @Test
    public void testPostRace() throws Exception {
        RaceDO input = createRace("RaceId", "SeriesId", "Track",123);
        doReturn(createRace("1", "SeriesId", "Track",123))
                .when(raceRepository).save(any(RaceDO.class));

        RaceDO actual = raceService.postRace("SeriesId", input);

        assertNotNull(actual);
        assertEquals("1", actual.getId());
        assertEquals("Track", actual.getTrack());
        verify(raceRepository).save(input);
    }

    @Test
    public void testPostTeamFailsWhenSeriesIdNotMatching() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceSeriesIdNotMatching");

        RaceDO input = createRace("RaceId", "SeriesId", "Track",123);

        raceService.postRace("WrongSeriesId", input);
    }

    @Test
    public void testPostTeamFailsWhenIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceNotYetCreated");

        RaceDO input = createRace(null, "SeriesId", "Track",123);

        raceService.postRace("SeriesId", input);
    }

    @Test
    public void testPostTeamFailsWhenSeriesIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceSeriesIsMissing");

        RaceDO input = createRace("RaceId", "", "Track",123);

        raceService.postRace("", input);
    }

    @Test
    public void testPostTeamFailsWhenTrackIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("RaceTrackIsMissing");

        RaceDO input = createRace("RaceId", "SeriesId", "",123);

        raceService.postRace("SeriesId", input);
    }

    @Test
    public void testDeleteTeam() throws Exception {
        raceService.deleteRace("SeriesId", "RaceId");
        verify(raceRepository).delete("RaceId");
    }

    @Test
    public void testDeleteTeamWhenTeamIdEmpty() throws Exception {
        raceService.deleteRace("SeriesId", "");
        verify(raceRepository, never()).delete(anyString());
    }

    private RaceDO createRace(String id, String seriesId, String track, long timestamp) {
        RaceDO race = new RaceDO();
        race.setId(id);
        race.setSeriesId(seriesId);
        race.setTrack(track);
        race.setTimestamp(timestamp);
        return race;
    }
}
