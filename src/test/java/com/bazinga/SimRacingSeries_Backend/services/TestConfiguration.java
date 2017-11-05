package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.RestExceptionHandler;
import com.bazinga.SimRacingSeries_Backend.repository.DriverRepository;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import com.bazinga.SimRacingSeries_Backend.repository.TeamRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@SpringBootApplication
@Import(RestExceptionHandler.class)
public class TestConfiguration {

    @Bean
    public SeriesRepository getSeriesRepository() {
        return mock(SeriesRepository.class);
    }

    @Bean
    public TeamRepository getTeamRepository() {
        return mock(TeamRepository.class);
    }

    @Bean
    public DriverRepository getDriverRepository() {
        return mock(DriverRepository.class);
    }
}
