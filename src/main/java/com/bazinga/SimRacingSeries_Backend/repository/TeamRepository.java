package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.TeamDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TeamRepository extends MongoRepository<TeamDO, String> {

    List<TeamDO> findBySeriesId(String seriesId);
}
