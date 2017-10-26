package com.bazinga.SimRacingSeries_Backend.series;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeriesServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testShouldReturnString() {
        ResponseEntity result = restTemplate.getForEntity("/series", String.class);

        assertEquals("Test", result.getBody());
    }
}