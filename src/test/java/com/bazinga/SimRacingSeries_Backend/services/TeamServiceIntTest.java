package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.SecurityConfiguration;
import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TeamService.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class TeamServiceIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private DriverService driverService;

    @Test
    public void testGetWorksWithoutAuthorisation() throws Exception {
        doReturn(Arrays.asList(new TeamDO(), new TeamDO())).when(teamRepository).findBySeriesId("SeriesId");

        mockMvc.perform(get("/api/teams/SeriesId"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(teamRepository).findBySeriesId("SeriesId");
    }

    @Test
    public void testPutFailsWhenAuthorisationMissing() throws Exception {
        String input = "{\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(put("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testPutFailsWhenNotAuthorisedForSeason() throws Exception {
        String input = "{\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(put("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testPutWorksWhenAuthorised() throws Exception {
        String input = "{\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(put("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostFailsWhenAuthorisationMissing() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(post("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testPostFailsWhenNotAuthorisedForSeason() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(post("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testPostWorksWhenAuthorised() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        mockMvc.perform(post("/api/teams/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void testDeleteFailsWhenAuthorisationMissing() throws Exception {
        mockMvc.perform(delete("/api/teams/SeriesId?teamId=1")
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testDeleteFailsWhenNotAuthorisedForSeason() throws Exception {
        mockMvc.perform(delete("/api/teams/SeriesId?teamId=1")
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testDeleteWorksWhenAuthorised() throws Exception {
        mockMvc.perform(delete("/api/teams/SeriesId?teamId=1")
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}
