package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SeriesServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private SeriesRepository seriesRepository;

    private SeriesService seriesService;

    @Before
    public void setUp() throws Exception {
        seriesRepository = mock(SeriesRepository.class);
        seriesService = new SeriesService(seriesRepository);
    }

    @Test
    public void testPutSeries() throws Exception {
        SeriesDO input = createSeries(null, "GT3", "GT3", "test");
        doReturn(createSeries("1", "GT3", "GT3", "test"))
                .when(seriesRepository).insert(input);

        SeriesDO actual = seriesService.putSeries(input);

        verify(seriesRepository).insert(input);
        assertEquals("1", actual.getId());
        assertEquals("GT3", actual.getName());
    }


    @Test
    public void testPutSeriesAlreadyExist() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesAlreadyExists");

        SeriesDO input = createSeries("1", "GT3", "GT3", "test");
        seriesService.putSeries(input);
    }

    @Test
    public void testPutSeriesSlugNameIsNotUnqiue() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SlugAlreadyUsed");

        SeriesDO input = createSeries(null, "GT3", "GT3", "test");
        doReturn(new SeriesDO()).when(seriesRepository).findBySlugNameIgnoreCase("GT3");
        seriesService.putSeries(input);
    }

    @Test
    public void testPutSeriesNameEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("NameIsEmpty");

        SeriesDO input = createSeries(null, "", "GT3", "test");
        seriesService.putSeries(input);
    }

    @Test
    public void testPutSeriesPasswordEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("PasswordIsEmpty");

        SeriesDO input = createSeries(null, "GT3", "GT3", "");
        seriesService.putSeries(input);
    }

    @Test
    public void testPutSeriesSlugNameEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("InvalidSlugName");

        SeriesDO input = createSeries(null, "GT3", "", "test");
        seriesService.putSeries(input);
    }

    @Test
    public void testSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("InvalidSlugName");

        SeriesDO input = createSeries(null, "GT3", "invalid$name", "test");
        seriesService.putSeries(input);
    }


    @Test
    public void testPostSeries() throws Exception {
        SeriesDO input = createSeries("SeriesId", "GT3", "GT3", "test");
        doReturn(createSeries("1", "GT3", "GT3", "test"))
                .when(seriesRepository).save(input);

        SeriesDO actual = seriesService.postSeries("SeriesId", input);

        verify(seriesRepository).save(input);
        assertEquals("1", actual.getId());
        assertEquals("GT3", actual.getName());
    }

    @Test
    public void testPostSeriesFailsWhenSeriesIdNotMatching() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIdNotMatching");

        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "test");
        seriesService.postSeries("WrongSeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenNameEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("NameIsEmpty");

        SeriesDO input = createSeries("SeriesId", "", "slugName", "test");
        seriesService.postSeries("SeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenPasswordEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("PasswordIsEmpty");

        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "");
        seriesService.postSeries("SeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("InvalidSlugName");

        SeriesDO input = createSeries("SeriesId", "GT3", "", "test");
        seriesService.postSeries("SeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("InvalidSlugName");

        SeriesDO input = createSeries("SeriesId", "GT3", "invalid$name", "test");
        seriesService.postSeries("SeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameIsNotUsedByAnotherSeries() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SlugAlreadyUsed");

        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "test");
        doReturn(createSeries("other", "test", "slugName", "test"))
                .when(seriesRepository).findBySlugNameIgnoreCase("slugName");
        seriesService.postSeries("SeriesId", input);
    }

    @Test
    public void testPostSeriesFailsWhenIdIsMissing() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesNotCreatedYet");

        SeriesDO input = createSeries("", "GT3", "slugName", "test");
        seriesService.postSeries("", input);
    }


    @Test
    public void testReadSeries() throws Exception {
        doReturn(createSeries("SeriesId", "Name", "Slug", "test"))
                .when(seriesRepository).findBySlugNameIgnoreCase("slugName");

        SeriesDO actual = seriesService.getSeries("slugName");

        verify(seriesRepository).findBySlugNameIgnoreCase("slugName");
        assertEquals("SeriesId", actual.getId());
        assertEquals("Name", actual.getName());
    }

    private SeriesDO createSeries(String id, String name, String slugName, String password) {
        SeriesDO series = new SeriesDO();
        series.setId(id);
        series.setName(name);
        series.setSlugName(slugName);
        series.setPassword(password);
        return series;
    }
}