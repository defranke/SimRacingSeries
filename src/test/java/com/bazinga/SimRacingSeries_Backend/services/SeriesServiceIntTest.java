package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.SecurityConfiguration;
import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SeriesService.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class SeriesServiceIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private SeriesRepository seriesRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void testGetWorksWithoutAuthorisation() throws Exception {
        doReturn(new SeriesDO()).when(seriesRepository).findBySlugNameIgnoreCase("SlugName");

        mockMvc.perform(get("/api/series/SlugName"))
                .andExpect(status().is2xxSuccessful());

        verify(seriesRepository).findBySlugNameIgnoreCase("SlugName");
    }

    @Test
    public void testPutWorksWithoutAuthorisation() throws Exception {
        String input = "{\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input).with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostFailsWhenAuthorisationMissing() throws Exception {
        String input = "{\"id\":\"SeriesId\",\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        mockMvc.perform(post("/api/series/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "WrongSeriesId")
    public void testPostFailsWhenNotAuthorisedForSeason() throws Exception {
        String input = "{\"id\":\"SeriesId\",\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        mockMvc.perform(post("/api/series/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "SeriesId")
    public void testPostWorksWhenAuthorised() throws Exception {
        String input = "{\"id\":\"SeriesId\",\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        mockMvc.perform(post("/api/series/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}
