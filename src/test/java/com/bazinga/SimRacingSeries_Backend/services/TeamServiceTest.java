package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = TeamService.class)
public class TeamServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private DriverService driverService;

    @Test
    public void testReadService() throws Exception {
        TeamDO team1 = new TeamDO();
        team1.setId("1");
        team1.setName("Test");
        TeamDO team2 = new TeamDO();
        team2.setId("2");
        team2.setName("Test2");
        doReturn(Arrays.asList(team1, team2)).when(teamRepository).findBySeriesId("SeriesId");

        mockMvc.perform(get("/api/teams?seriesId=SeriesId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Test2")));
    }

    @Test
    public void testDeleteTeam() throws Exception {
        mockMvc.perform(delete("/api/teams?teamId=TeamId"))
                .andExpect(status().isOk());
        verify(teamRepository).delete("TeamId");
        verify(driverService).deleteDriversOfTeam("TeamId");
    }

    @Test
    public void testDeleteTeamWhenIdEmpty() throws Exception {
        mockMvc.perform(delete("/api/teams?teamId="))
                .andExpect(status().isOk());
        verify(teamRepository, never()).delete(anyString());
    }

    @Test
    public void testPutTeam() throws Exception {
        String input = "{\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        TeamDO output = new TeamDO();
        output.setId("TeamId");
        output.setSeriesId("SeriesId");
        output.setName("Team1");
        doReturn(output).when(teamRepository).insert(any(TeamDO.class));

        mockMvc.perform(put("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TeamId")))
                .andExpect(jsonPath("name", is("Team1")));
    }

    @Test
    public void testPutTeamIdIsNotEmpty() throws Exception {
        String input = "{\"id\":\"123\",\"seriesId\":\"Tes\",\"name\":\"Team1\"}";
        mockMvc.perform(put("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("TeamAlreadySaved")));
    }

    @Test
    public void testPutTeamSeriesIdIsEmpty() throws Exception {
        String input = "{\"seriesId\":\"\",\"name\":\"Team1\"}";
        mockMvc.perform(put("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("SeriesIsMissing")));
    }

    @Test
    public void testPutTeamNameIsEmpty() throws Exception {
        String input = "{\"seriesId\":\"123\",\"name\":\"\"}";
        mockMvc.perform(put("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("TeamNameIsMissing")));
    }

    @Test
    public void testPostTeam() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"SeriesId\",\"name\":\"Team1\"}";

        TeamDO output = new TeamDO();
        output.setId("TeamId1");
        doReturn(output).when(teamRepository).save(any(TeamDO.class));

        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TeamId1")));
    }

    @Test
    public void testPostIdIsEmpty() throws Exception {
        String input = "{\"id\":\"\",\"seriesId\":\"Test\",\"name\":\"Team1\"}";
        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("TeamNotSavedYet")));
    }

    @Test
    public void testPostSeriesIdIsEmpty() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"\",\"name\":\"Team1\"}";
        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("SeriesIsMissing")));
    }

    @Test
    public void testPostTeamNameIsEmpty() throws Exception {
        String input = "{\"id\":\"TeamId\",\"seriesId\":\"123\",\"name\":\"\"}";
        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("TeamNameIsMissing")));
    }
}
