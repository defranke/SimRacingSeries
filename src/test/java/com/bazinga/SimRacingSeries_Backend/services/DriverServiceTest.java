package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class DriverServiceTest {

    private DriverRepository driverRepository;

    private DriverService driverService;

    @Before
    public void setUp() throws Exception {
        driverRepository = mock(DriverRepository.class);
        driverService = new DriverService(driverRepository);
    }

    @Test
    public void testReadService() throws Exception {
        DriverDO driver1 = createDriver("1", "SeriesId", "TeamId", "Test");
        DriverDO driver2 = createDriver("2", "SeriesId", "TeamId", "Test2");
        doReturn(Arrays.asList(driver1, driver2)).when(driverRepository).findBySeriesId("SeriesId");

        List<DriverDO> drivers = driverService.getDriverFor("SeriesId");
        assertEquals(2, drivers.size());
        assertEquals("1", drivers.get(0).getId());
        assertEquals("Test", drivers.get(0).getName());
        assertEquals("2", drivers.get(1).getId());
        assertEquals("Test2", drivers.get(1).getName());
    }

    @Test
    public void testCreateDriver() throws Exception {
        doReturn(createDriver("1", "SeriesId", "TeamId", "Name")).when(driverRepository).insert(any(DriverDO.class));

        DriverDO driver = driverService.putDriver("SeriesId", createDriver(null, "SeriesId", "TeamId", "Name"));

        assertEquals("1", driver.getId());
        assertEquals("Name", driver.getName());
        verify(driverRepository).insert(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenSeriesIdNotMatching() throws Exception {
        DriverDO driver = createDriver("", "SeriesId", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.putDriver("SeriesIdNotMatching", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("SeriesIdNotMatching", thrownException.getLocalizedMessage());
    }

    @Test
    public void testCreateDriverFailsWhenIdNotEmpty() throws Exception {
        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.putDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        assertEquals("DriverAlreadyCreated", thrownException.getLocalizedMessage());
        verify(driverRepository, never()).insert(any(DriverDO.class));
    }

    @Test
    public void testCreateDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        DriverDO driver = createDriver("", "", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.putDriver("", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverSeriesIsMissing", thrownException.getLocalizedMessage());
    }

    @Test
    public void testCreateDriverFailsWhenTeamIdIsEmpty() throws Exception {
        DriverDO driver = createDriver("", "SeriesId", "", "Name");

        Exception thrownException = null;
        try {
            driverService.putDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverTeamIsMissing", thrownException.getLocalizedMessage());
    }

    @Test
    public void testCreateDriverFailsWhenNameIsEmpty() throws Exception {
        DriverDO driver = createDriver("", "SeriesId", "TeamId", "");

        Exception thrownException = null;
        try {
            driverService.putDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverNameIsMissing", thrownException.getLocalizedMessage());
    }


    @Test
    public void testSaveDriver() throws Exception {
        doReturn(createDriver("1", "SeriesId", "TeamId", "Name"))
                .when(driverRepository).save(any(DriverDO.class));

        DriverDO driver = driverService.postDriver("SeriesId", createDriver("1", "SeriesId", "TeamId", "Name"));

        assertEquals("1", driver.getId());
        assertEquals("Name", driver.getName());
        verify(driverRepository).save(any(DriverDO.class));
    }

    @Test
    public void testSaveDriverFailsWhenSeriesIdNotMatching() throws Exception {
        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.postDriver("WrongSeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("SeriesIdNotMatching", thrownException.getLocalizedMessage());
    }

    @Test
    public void testSaveDriverFailsWhenIdIsEmpty() throws Exception {
        DriverDO driver = createDriver("", "SeriesId", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.postDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverNotCreatedYet", thrownException.getLocalizedMessage());
    }

    @Test
    public void testSaveDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        DriverDO driver = createDriver("1", "", "TeamId", "Name");

        Exception thrownException = null;
        try {
            driverService.postDriver("", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverSeriesIsMissing", thrownException.getLocalizedMessage());
    }

    @Test
    public void testSaveDriverFailsWhenTeamIdIsEmpty() throws Exception {
        DriverDO driver = createDriver("1", "SeriesId", "", "Name");

        Exception thrownException = null;
        try {
            driverService.postDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverTeamIsMissing", thrownException.getLocalizedMessage());
    }

    @Test
    public void testSaveDriverFailsWhenNameIsEmpty() throws Exception {
        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "");

        Exception thrownException = null;
        try {
            driverService.postDriver("SeriesId", driver);
        } catch (Exception e) {
            thrownException = e;
        }
        assertNotNull(thrownException);
        verify(driverRepository, never()).insert(any(DriverDO.class));
        assertEquals("DriverNameIsMissing", thrownException.getLocalizedMessage());
    }

    @Test
    public void testDeleteDriver() throws Exception {
        driverService.deleteDriver("SeriesId", "1");

        verify(driverRepository).delete("1");
    }

    @Test
    public void testDeleteDriverWhenIdEmpty() throws Exception {
        driverService.deleteDriver("SeriesId", "");

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

    private DriverDO createDriver(String id, String seriesId, String teamId, String name) {
        DriverDO driver = new DriverDO();
        driver.setId(id);
        driver.setSeriesId(seriesId);
        driver.setTeamId(teamId);
        driver.setName(name);
        return driver;
    }
}
