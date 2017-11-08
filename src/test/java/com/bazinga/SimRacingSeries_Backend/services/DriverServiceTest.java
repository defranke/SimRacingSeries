package com.bazinga.SimRacingSeries_Backend.services;


import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class DriverServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIdNotMatching");

        DriverDO driver = createDriver("", "SeriesId", "TeamId", "Name");
        driverService.putDriver("SeriesIdNotMatching", driver);
    }

    @Test
    public void testCreateDriverFailsWhenIdNotEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverAlreadyCreated");

        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "Name");
        driverService.putDriver("SeriesId", driver);
    }

    @Test
    public void testCreateDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverSeriesIsMissing");

        DriverDO driver = createDriver("", "", "TeamId", "Name");
        driverService.putDriver("", driver);
    }

    @Test
    public void testCreateDriverFailsWhenTeamIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverTeamIsMissing");

        DriverDO driver = createDriver("", "SeriesId", "", "Name");
        driverService.putDriver("SeriesId", driver);
    }

    @Test
    public void testCreateDriverFailsWhenNameIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverNameIsMissing");

        DriverDO driver = createDriver("", "SeriesId", "TeamId", "");
        driverService.putDriver("SeriesId", driver);
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
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("SeriesIdNotMatching");

        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "Name");
        driverService.postDriver("WrongSeriesId", driver);
    }

    @Test
    public void testSaveDriverFailsWhenIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverNotCreatedYet");

        DriverDO driver = createDriver("", "SeriesId", "TeamId", "Name");
        driverService.postDriver("SeriesId", driver);
    }

    @Test
    public void testSaveDriverFailsWhenSeriesIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverSeriesIsMissing");

        DriverDO driver = createDriver("1", "", "TeamId", "Name");
        driverService.postDriver("", driver);
    }

    @Test
    public void testSaveDriverFailsWhenTeamIdIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverTeamIsMissing");

        DriverDO driver = createDriver("1", "SeriesId", "", "Name");
        driverService.postDriver("SeriesId", driver);
    }

    @Test
    public void testSaveDriverFailsWhenNameIsEmpty() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("DriverNameIsMissing");

        DriverDO driver = createDriver("1", "SeriesId", "TeamId", "");
        driverService.postDriver("SeriesId", driver);
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
