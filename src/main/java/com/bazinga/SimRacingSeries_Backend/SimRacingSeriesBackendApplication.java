package com.bazinga.SimRacingSeries_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SimRacingSeriesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimRacingSeriesBackendApplication.class, args);
	}
}
