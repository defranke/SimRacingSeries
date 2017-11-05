package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.DriverDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DriverRepository extends MongoRepository<DriverDO, String> {

    List<DriverDO> findBySeriesId(String seriesId);

    List<DriverDO> findByTeamId(String teamId);
}
