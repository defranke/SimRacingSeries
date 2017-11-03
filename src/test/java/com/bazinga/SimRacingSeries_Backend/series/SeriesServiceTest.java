package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = SeriesService.class)
public class SeriesServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeriesRepository seriesRepository;

    @Before
    public void setup() {
    }

    @Test
    public void testPutSeries() throws Exception {
        String input = "{\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        SeriesDO output = new SeriesDO();
        output.setId("TestID");
        output.setName("GT3");
        doReturn(output).when(seriesRepository).insert(any(SeriesDO.class));

        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TestID")))
                .andExpect(jsonPath("name", is("GT3")));
    }

    @Test
    public void testPutSeriesNameEmpty() throws Exception {
        String input = "{\"name\":\"\", \"slugName\":\"name\", \"password\":\"test\"}";
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("NameIsEmpty")));
    }

    @Test
    public void testPutSeriesPasswordEmpty() throws Exception {
        String input = "{\"name\":\"Test\", \"slugName\":\"name\", \"password\":\"\"}";
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("PasswordIsEmpty")));
    }

    @Test
    public void testPutSeriesSlugNameEmpty() throws Exception {
        String input = "{\"name\":\"bla\", \"slugName\":\"\", \"password\":\"test\"}";
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("InvalidSlugName")));
    }

    @Test
    public void testSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        String input = "{\"name\":\"GT3\", \"slugName\":\"invalid$name\", \"password\":\"test\"}";
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("InvalidSlugName")));
    }

    @Test
    public void testPutSeriesSlugNameIsUnqiue() throws Exception {
        String input = "{\"name\":\"GT3\", \"slugName\":\"GT3\", \"password\":\"test\"}";
        doReturn(mock(SeriesDO.class)).when(seriesRepository).findBySlugName("GT3");
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("SlugAlreadyUsed")));
    }

    @Test
    public void testPostSeries() throws Exception {
        String input = "{\"id\":\"TestID\",\"name\":\"GT3\",\"slugName\":\"GT3\", \"password\":\"test\"}";

        SeriesDO output = new SeriesDO();
        output.setId("TestID");
        output.setName("GT3");
        doReturn(output).when(seriesRepository).save(any(SeriesDO.class));

        mockMvc.perform(post("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TestID")))
                .andExpect(jsonPath("name", is("GT3")));
    }

    @Test
    public void testPostSeriesNameEmpty() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"\", \"slugName\":\"name\", \"password\":\"test\"}";
        mockMvc.perform(post("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("NameIsEmpty")));
    }

    @Test
    public void testPostSeriesPasswordEmpty() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"Test\", \"slugName\":\"name\", \"password\":\"\"}";
        mockMvc.perform(post("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("PasswordIsEmpty")));
    }

    @Test
    public void testPostSeriesSlugNameEmpty() throws Exception {
        String input = "{\"id\":\"1\",\"name\":\"bla\", \"slugName\":\"\", \"password\":\"test\"}";
        mockMvc.perform(post("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("InvalidSlugName")));
    }

    @Test
    public void testPostSeriesSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        String input = "{\"name\":\"GT3\", \"slugName\":\"invalid$name\", \"password\":\"test\"}";
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("InvalidSlugName")));
    }

    @Test
    public void testPostSeriesSlugNameIsNotUsedByAnotherSeries() throws Exception {
        String input = "{\"id\":\"TestId\", \"name\":\"GT3\", \"slugName\":\"GT3\", \"password\":\"test\"}";
        SeriesDO otherSeries = new SeriesDO();
        otherSeries.setId("Different");
        doReturn(otherSeries).when(seriesRepository).findBySlugName("GT3");
        mockMvc.perform(put("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("SlugAlreadyUsed")));
    }

    @Test
    public void testPostSeriesThrowsExceptionWhenIdMissing() throws Exception {
        String input = "{\"name\":\"GT3\", \"password\":\"test\"}";
        mockMvc.perform(post("/api/series").contentType(MediaType.APPLICATION_JSON).content(input))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", is("SeriesNotCreatedYet")));
    }

    @Test
    public void testReadService() throws Exception {
        SeriesDO series = new SeriesDO();
        series.setId("TestID");
        series.setName("GT3");
        series.setSlugName("slugName");
        series.setDescription("description");
        series.setPassword("password");
        series.setIsPublic(true);
        doReturn(series).when(seriesRepository).findBySlugName("slugName");

        mockMvc.perform(get("/api/series?slugName=slugName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("TestID")))
                .andExpect(jsonPath("name", is("GT3")))
                .andExpect(jsonPath("slugName", is("slugName")))
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("description", is("description")))
                .andExpect(jsonPath("isPublic", is(true)));
    }
}