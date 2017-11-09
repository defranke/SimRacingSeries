package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.RaceDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RaceRepository extends MongoRepository<RaceDO, String> {
    List<RaceDO> findBySeriesId(String seriesId);
}
