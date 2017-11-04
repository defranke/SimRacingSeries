package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = GeneralService.class)
public class GeneralServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeriesRepository seriesRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void testReadService() throws Exception {
        SeriesDO mockSeries = new SeriesDO();
        mockSeries.setId("SeriesId");
        mockSeries.setName("SERIES");
        TeamDO team1 = new TeamDO();
        team1.setName("TEAM1");
        TeamDO team2 = new TeamDO();
        team2.setName("TEAM2");
        doReturn(mockSeries).when(seriesRepository).findBySlugNameIgnoreCase("TEST");
        doReturn(Arrays.asList(team1, team2)).when(teamRepository).findBySeriesId("SeriesId");

        mockMvc.perform(get("/api/general/completeSeries?slugName=TEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.series.name", is("SERIES")))
                .andExpect(jsonPath("$.teams[0].name", is("TEAM1")))
                .andExpect(jsonPath("$.teams[1].name", is("TEAM2")));
    }

    @Test
    public void testThrowsNotFound() throws Exception {
        doReturn(null).when(seriesRepository).findBySlugNameIgnoreCase("TEST");
        mockMvc.perform(get("/api/general/completeSeries?slugName=TEST"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("SeriesNotFound")));
    }
}
