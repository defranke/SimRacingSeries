package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<SeriesDO, String> {

    //SeriesDO findByName(String name);
}
