package com.bazinga.SimRacingSeries_Backend.repository;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeriesRepository extends MongoRepository<SeriesDO, String> {

    SeriesDO findByName(String name);
    SeriesDO findBySlugNameIgnoreCase(String slugName);

    List<SeriesDO> findByNameLike(String name);
}
