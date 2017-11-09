package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.SecurityConfiguration;
import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.model.RaceDO;
import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import com.bazinga.SimRacingSeries_Backend.repository.RaceRepository;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {GeneralService.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class GeneralServiceIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private SeriesRepository seriesRepository;

    @MockBean
    private RaceRepository raceRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void testGetWorksWithoutAuthorisation() throws Exception {
        SeriesDO series = new SeriesDO();
        series.setId("SeriesId");
        doReturn(Arrays.asList(new DriverDO())).when(driverRepository).findByTeamId("SeriesId");
        doReturn(Arrays.asList(new TeamDO())).when(teamRepository).findBySeriesId("SeriesId");
        doReturn(Arrays.asList(new RaceDO())).when(raceRepository).findBySeriesId("SeriesId");
        doReturn(series).when(seriesRepository).findBySlugNameIgnoreCase("SlugName");

        mockMvc.perform(get("/api/general/completeSeries?slugName=SlugName"))
                .andExpect(status().is2xxSuccessful());

        verify(driverRepository).findBySeriesId("SeriesId");
    }

}
