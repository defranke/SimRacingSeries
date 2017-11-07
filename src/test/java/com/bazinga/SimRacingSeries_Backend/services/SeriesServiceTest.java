package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SeriesServiceTest {

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
        SeriesDO input = createSeries("1", "GT3", "GT3", "test");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("SeriesAlreadyExists", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPutSeriesSlugNameIsNotUnqiue() throws Exception {
        SeriesDO input = createSeries(null, "GT3", "GT3", "test");
        doReturn(new SeriesDO()).when(seriesRepository).findBySlugNameIgnoreCase("GT3");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("SlugAlreadyUsed", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPutSeriesNameEmpty() throws Exception {
        SeriesDO input = createSeries(null, "", "GT3", "test");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("NameIsEmpty", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPutSeriesPasswordEmpty() throws Exception {
        SeriesDO input = createSeries(null, "GT3", "GT3", "");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("PasswordIsEmpty", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPutSeriesSlugNameEmpty() throws Exception {
        SeriesDO input = createSeries(null, "GT3", "", "test");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("InvalidSlugName", thrownException.getLocalizedMessage());
    }

    @Test
    public void testSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        SeriesDO input = createSeries(null, "GT3", "invalid$name", "test");

        Exception thrownException = null;
        try {
            seriesService.putSeries(input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("InvalidSlugName", thrownException.getLocalizedMessage());
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
        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "test");

        Exception thrownException = null;
        try {
            seriesService.postSeries("WrongSeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("SeriesIdNotMatching", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenNameEmpty() throws Exception {
        SeriesDO input = createSeries("SeriesId", "", "slugName", "test");

        Exception thrownException = null;
        try {
            seriesService.postSeries("SeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("NameIsEmpty", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenPasswordEmpty() throws Exception {
        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "");

        Exception thrownException = null;
        try {
            seriesService.postSeries("SeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("PasswordIsEmpty", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameEmpty() throws Exception {
        SeriesDO input = createSeries("SeriesId", "GT3", "", "test");

        Exception thrownException = null;
        try {
            seriesService.postSeries("SeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("InvalidSlugName", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameOnlyContainsUrlValidCharacters() throws Exception {
        SeriesDO input = createSeries("SeriesId", "GT3", "invalid$name", "test");

        Exception thrownException = null;
        try {
            seriesService.postSeries("SeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("InvalidSlugName", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenSlugNameIsNotUsedByAnotherSeries() throws Exception {
        SeriesDO input = createSeries("SeriesId", "GT3", "slugName", "test");
        doReturn(createSeries("other", "test", "slugName", "test"))
                .when(seriesRepository).findBySlugNameIgnoreCase("slugName");

        Exception thrownException = null;
        try {
            seriesService.postSeries("SeriesId", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("SlugAlreadyUsed", thrownException.getLocalizedMessage());
    }

    @Test
    public void testPostSeriesFailsWhenIdIsMissing() throws Exception {
        SeriesDO input = createSeries("", "GT3", "slugName", "test");

        Exception thrownException = null;
        try {
            seriesService.postSeries("", input);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(seriesRepository, never()).insert(any(SeriesDO.class));
        assertEquals("SeriesNotCreatedYet", thrownException.getLocalizedMessage());
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