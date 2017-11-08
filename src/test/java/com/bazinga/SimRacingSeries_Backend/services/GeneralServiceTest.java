package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.CompleteSeriesTO;
import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GeneralServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private DriverRepository driverRepository;
    private TeamRepository teamRepository;
    private SeriesRepository seriesRepository;

    private GeneralService generalService;

    @Before
    public void setUp() throws Exception {
        driverRepository = mock(DriverRepository.class);
        teamRepository = mock(TeamRepository.class);
        seriesRepository = mock(SeriesRepository.class);
        generalService = new GeneralService(seriesRepository, teamRepository, driverRepository);
    }

    @Test
    public void testReadService() throws Exception {
        SeriesDO mockSeries = new SeriesDO();
        mockSeries.setId("SeriesId");
        mockSeries.setName("SERIES");
        TeamDO team1 = new TeamDO();
        team1.setName("TEAM1");
        TeamDO team2 = new TeamDO();
        team2.setName("TEAM2");
        DriverDO driver1 = new DriverDO();
        driver1.setId("DRIVER1");
        DriverDO driver2 = new DriverDO();
        driver2.setId("DRIVER2");
        doReturn(mockSeries).when(seriesRepository).findBySlugNameIgnoreCase("TEST");
        doReturn(Arrays.asList(team1, team2)).when(teamRepository).findBySeriesId("SeriesId");
        doReturn(Arrays.asList(driver1, driver2)).when(driverRepository).findBySeriesId("SeriesId");

        CompleteSeriesTO data = generalService.getCompleteSeries("TEST");

        assertEquals("SERIES", data.getSeries().getName());
        assertEquals(2, data.getTeams().size());
        assertEquals("TEAM1", data.getTeams().get(0).getName());
        assertEquals("TEAM2", data.getTeams().get(1).getName());
        verify(seriesRepository).findBySlugNameIgnoreCase("TEST");
        verify(teamRepository).findBySeriesId("SeriesId");
        verify(driverRepository).findBySeriesId("SeriesId");
    }

    @Test
    public void testThrowsNotFound() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesNotFound");

        doReturn(null).when(seriesRepository).findBySlugNameIgnoreCase("TEST");
        generalService.getCompleteSeries("TEST");
    }
}
