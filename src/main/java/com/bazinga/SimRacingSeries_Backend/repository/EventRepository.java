package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.EventDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<EventDO, String> {

    List<EventDO> findBySeriesId(String seriesId);

    List<EventDO> findByRaceId(String raceId);
}
