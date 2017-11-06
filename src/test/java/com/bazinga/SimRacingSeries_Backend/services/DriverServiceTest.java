package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.TestSecurityConfiguration;
import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = {DriverService.class, TestSecurityConfiguration.class} )
@AutoConfigureMockMvc
//@WebMvcTest(controllers = DriverService.class)
public class DriverServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    @Autowired
    private DriverService driverService;

    @Test
    public void testReadService() throws Exception {
        DriverDO driver1 = new DriverDO();
        driver1.setId("1");
        driver1.setName("Test");
        DriverDO driver2 = new DriverDO();
        driver2.setId("2");
        driver2.setName("Test2");
        doReturn(Arrays.asList(driver1, driver2)).when(driverRepository).findBySeriesId("SeriesId");

        mockMvc.perform(get("/api/drivers?seriesId=SeriesId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Test2")));
    }

    @Test
    public void testCreateDriver() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";
        DriverDO driver = new DriverDO();
        driver.setId("123");
        doReturn(driver).when(driverRepository).insert(any(DriverDO.class));

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("123")));
        verify(driverRepository).insert(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenIdNotEmpty() throws Exception {
        String input = "{\"id\":\"123\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverAlreadyCreated")));
        verify(driverRepository, never()).insert(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"\",\"seriesId\":\"\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverSeriesIsMissing")));
        verify(driverRepository, never()).insert(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenTeamIdIsEmpty() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"\",\"seriesId\":\"123\",\"teamId\":\"\"}";

        mockMvc.perform(put("/api/drivers/SeriesId").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverTeamIsMissing")));
        verify(driverRepository, never()).save(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenNameIsEmpty() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(put("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverNameIsMissing")));
        verify(driverRepository, never()).insert(any(DriverDO.class));
    }

    @Test
    public void testSaveDriver() throws Exception {
        String input = "{\"id\":\"123\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";
        DriverDO driver = new DriverDO();
        driver.setId("Bla");
        doReturn(driver).when(driverRepository).save(any(DriverDO.class));

        mockMvc.perform(post("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("Bla")));
        verify(driverRepository).save(any(DriverDO.class));
    }

    @Test
    public void testSaveDriverFailsWhenIdIsEmpty() throws Exception {
        String input = "{\"id\":\"\",\"name\":\"Test\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverNotCreatedYet")));
        verify(driverRepository, never()).save(any(DriverDO.class));
    }

    @Test
    public void testSaveDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        String input = "{\"id\":\"123\",\"name\":\"\",\"seriesId\":\"\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverSeriesIsMissing")));
        verify(driverRepository, never()).save(any(DriverDO.class));
    }

    @Test
    public void testSaveDriverFailsWhenTeamIdIsEmpty() throws Exception {
        String input = "{\"id\":\"123\",\"name\":\"\",\"seriesId\":\"123\",\"teamId\":\"\"}";

        mockMvc.perform(post("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverTeamIsMissing")));
        verify(driverRepository, never()).save(any(DriverDO.class));
    }

    @Test
    public void testSaveDriverFailsWhenNameIsEmpty() throws Exception {
        String input = "{\"id\":\"123\",\"name\":\"\",\"seriesId\":\"SeriesId\",\"teamId\":\"123\"}";

        mockMvc.perform(post("/api/drivers").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("DriverNameIsMissing")));
        verify(driverRepository, never()).save(any(DriverDO.class));
    }

    @Test
    public void testDeleteDriver() throws Exception {
        mockMvc.perform(delete("/api/drivers?driverId=DriverId"))
                .andExpect(status().isOk());
        verify(driverRepository).delete("DriverId");
    }

    @Test
    public void testDeleteDriverWhenIdEmpty() throws Exception {
        mockMvc.perform(delete("/api/drivers?driverId="))
                .andExpect(status().isOk());
        verify(driverRepository, never()).delete(anyString());
    }

    @Test
    public void testDeleteDriversOfTeam() throws Exception {
        DriverDO driver1 = new DriverDO();
        DriverDO driver2 = new DriverDO();
        doReturn(Arrays.asList(driver1, driver2)).when(driverRepository).findByTeamId("TeamId");

        driverService.deleteDriversOfTeam("TeamId");

        verify(driverRepository, times(2)).delete(anyString());
    }

    @Test
    public void testDeleteDriversOfTeamDoesNothingWhenTeamIsNull() throws Exception {
        driverService.deleteDriversOfTeam(null);

        verify(driverRepository, never()).delete(anyString());
    }
}
