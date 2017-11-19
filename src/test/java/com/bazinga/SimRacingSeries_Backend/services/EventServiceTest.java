package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.EventDO;
import com.bazinga.SimRacingSeries_Backend.repository.EventRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static com.bazinga.SimRacingSeries_Backend.model.EventDO.EventType.QUALIFYING;
import static com.bazinga.SimRacingSeries_Backend.model.EventDO.EventType.RACE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class EventServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private EventRepository eventRepository;

    private EventService eventService;

    @Before
    public void setUp() throws Exception {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    public void testReadService() throws Exception {
        EventDO event1 = createEvent("1", "SeriesId", "RaceId", QUALIFYING, "Test");
        EventDO event2 = createEvent("2", "SeriesId", "RaceId", RACE, "Test2");
        doReturn(Arrays.asList(event1, event2)).when(eventRepository).findByRaceId("RaceId");

        List<EventDO> events = eventService.getEventsFor("RaceId");
        assertEquals(2, events.size());
        assertEquals("1", events.get(0).getId());
        assertEquals("Test", events.get(0).getDescription());
        assertEquals("2", events.get(1).getId());
        assertEquals("Test2", events.get(1).getDescription());
    }

    private EventDO createEvent(String id, String seriesId, String raceId, EventDO.EventType type, String title) {
        EventDO event = new EventDO();
        event.setId(id);
        event.setSeriesId(seriesId);
        event.setRaceId(raceId);
        event.setType(type);
        event.setTitle(title);
        return event;
    }
}
