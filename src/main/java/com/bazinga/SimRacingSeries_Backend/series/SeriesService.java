package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/series")
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody void putSeries() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        repository.deleteAll();
        repository.save(new SeriesDO("Test1"));
        for(SeriesDO s : repository.findAll()) {
            System.out.println(s.getId());
            System.out.println(s.getName());
        }
        return "Test";
    }
}
