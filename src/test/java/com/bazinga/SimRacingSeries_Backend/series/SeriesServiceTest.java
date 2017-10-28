package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SeriesServiceTest {

    private MockMvc mockMvc;

    private SeriesRepository seriesRepository;
    private SeriesService service;

    @Before
    public void setup() {
        seriesRepository = mock(SeriesRepository.class);
        service = new SeriesService(seriesRepository);

        mockMvc = MockMvcBuilders.standaloneSetup(service).build();
    }

    @Test
    public void testReadService() throws Exception {
        SeriesDO series = new SeriesDO("GT3");
        series.setId("TestID");
        doReturn(series).when(seriesRepository).findByName("GT3");

        String expectedResult = "{\"id\":\"TestID\",\"name\":\"GT3\"}";

        mockMvc.perform(MockMvcRequestBuilders.get("/series?name=GT3"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult))
                .andExpect(jsonPath("id", is("TestID")));
    }
}