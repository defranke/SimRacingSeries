package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
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


public class TeamServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private TeamRepository teamRepository;

    private DriverService driverService;
    private TeamService teamService;

    @Before
    public void setUp() throws Exception {
        teamRepository = mock(TeamRepository.class);
        driverService = mock(DriverService.class);
        teamService = new TeamService(teamRepository, driverService);
    }

    @Test
    public void testReadService() throws Exception {
        doReturn(Arrays.asList(createTeam("1", "SeriesId", "Test"),
                createTeam("2", "SeriesId", "Name")))
                .when(teamRepository).findBySeriesId("SeriesId");

        List<TeamDO> teams = teamService.getTeamsFor("SeriesId");

        assertEquals(2, teams.size());
        assertEquals("Test", teams.get(0).getName());
        assertEquals("Name", teams.get(1).getName());
    }

    @Test
    public void testDeleteTeam() throws Exception {
        teamService.deleteTeam("SeriesId", "TeamId");
        verify(teamRepository).delete("TeamId");
        verify(driverService).deleteDriversOfTeam("TeamId");
    }

    @Test
    public void testDeleteTeamWhenTeamIdEmpty() throws Exception {
        teamService.deleteTeam("SeriesId", "");
        verify(teamRepository, never()).delete("TeamId");
        verify(driverService, never()).deleteDriversOfTeam("TeamId");
    }

    @Test
    public void testPutTeam() throws Exception {
        TeamDO input = createTeam(null, "SeriesId", "Name");
        doReturn(createTeam("1", "SeriesId", "Name"))
                .when(teamRepository).insert(any(TeamDO.class));

        TeamDO actual = teamService.putTeam("SeriesId", input);

        assertNotNull(actual);
        assertEquals("1", actual.getId());
        assertEquals("Name", actual.getName());
        verify(teamRepository).insert(input);
    }

    @Test
    public void testPutTeamFailsWhenSeriesIdNotMatching() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIdNotMatching");

        TeamDO input = createTeam(null, "SeriesId", "Name");

        teamService.putTeam("WrongSeriesId", input);
    }

    @Test
    public void testPutTeamFailsWhenIdIsMissing() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("TeamAlreadySaved");

        TeamDO input = createTeam("1", "SeriesId", "Name");

        teamService.putTeam("SeriesId", input);
    }

    @Test
    public void testPutTeamFailsWhenSeriesIdIsMissing() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIsMissing");

        TeamDO input = createTeam(null, "", "Name");

        teamService.putTeam("", input);
    }

    @Test
    public void testPutTeamFailsWhenNameIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("TeamNameIsMissing");

        TeamDO input = createTeam(null, "SeriesId", "");

        teamService.putTeam("SeriesId", input);
    }

    @Test
    public void testPostTeam() throws Exception {
        TeamDO input = createTeam("TeamId", "SeriesId", "Name");
        doReturn(createTeam("1", "SeriesId", "Name"))
                .when(teamRepository).save(any(TeamDO.class));

        TeamDO actual = teamService.postTeam("SeriesId", input);

        assertNotNull(actual);
        assertEquals("1", actual.getId());
        assertEquals("Name", actual.getName());
        verify(teamRepository).save(input);
    }

    @Test
    public void testPostTeamFailsWhenSeriesIdNotMatching() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIdNotMatching");

        TeamDO input = createTeam("1", "SeriesId", "Name");

        teamService.postTeam("WrongSeriesId", input);
    }

    @Test
    public void testPostTeamFailsWhenIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("TeamNotSavedYet");

        TeamDO input = createTeam("", "SeriesId", "Name");

        teamService.postTeam("SeriesId", input);
    }

    @Test
    public void testPostTeamFailsWhenSeriesIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIsMissing");

        TeamDO input = createTeam("2", "", "Name");

        teamService.postTeam("", input);
    }

    @Test
    public void testPostTeamFailsWhenNameIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("TeamNameIsMissing");

        TeamDO input = createTeam("2", "SeriesId", "");

        teamService.postTeam("SeriesId", input);
    }

    private TeamDO createTeam(String id, String seriesId, String name) {
        TeamDO team = new TeamDO();
        team.setId(id);
        team.setSeriesId(seriesId);
        team.setName(name);
        return team;
    }
}
