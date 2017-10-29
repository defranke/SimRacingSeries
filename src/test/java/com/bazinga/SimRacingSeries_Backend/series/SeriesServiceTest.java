package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
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
    public void testPutSeries() throws Exception {
        String input = "{\"name\":\"GT3\"}";

        SeriesDO output = new SeriesDO("GT3");
        output.setId("TestID");
        doReturn(output).when(seriesRepository).insert(any(SeriesDO.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TestID")))
                .andExpect(jsonPath("name", is("GT3")));
    }

    @Test(expected = NestedServletException.class)
    public void testPutSeriesSlugNameIsUnqiue() throws Exception {
        String input = "{\"name\":\"GT3\", \"slugName\":\"GT3\"}";
        doReturn(mock(SeriesDO.class)).when(seriesRepository).findBySlugName("GT3");

        mockMvc.perform(MockMvcRequestBuilders.put("/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testReadService() throws Exception {
        SeriesDO series = new SeriesDO("GT3");
        series.setId("TestID");
        doReturn(series).when(seriesRepository).findByName("GT3");

        mockMvc.perform(MockMvcRequestBuilders.get("/series?name=GT3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TestID")))
                .andExpect(jsonPath("name", is("GT3")));
    }
}