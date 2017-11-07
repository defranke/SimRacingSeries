package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.SecurityConfiguration;
import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DriverService.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class DriverServiceIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void testGetWorksWithoutAuthorisation() throws Exception {
        doReturn(Arrays.asList(new DriverDO())).when(driverRepository).findByTeamId("SeriesId");

        mockMvc.perform(get("/api/drivers/SeriesId"))
                .andExpect(status().is2xxSuccessful());

        verify(driverRepository).findBySeriesId("SeriesId");
    }

    @Test
    public void testPutFailsWhenAuthorisationMissing() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testPutFailsWhenNotAuthorisedForSeason() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testPutWorksWhenAuthorised() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void testPostFailsWhenAuthorisationMissing() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testPostFailsWhenNotAuthorisedForSeason() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testPostWorksWhenAuthorised() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void testDeleteFailsWhenAuthorisationMissing() throws Exception {
        mockMvc.perform(delete("/api/drivers/SeriesId?driverId=1")
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testDeleteFailsWhenNotAuthorisedForSeason() throws Exception {
        mockMvc.perform(delete("/api/drivers/SeriesId?driverId=1")
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testDeleteWorksWhenAuthorised() throws Exception {
        mockMvc.perform(delete("/api/drivers/SeriesId?driverId=1")
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}
