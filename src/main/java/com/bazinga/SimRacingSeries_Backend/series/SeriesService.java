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
    private SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    void putSeries() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    SeriesDO getSeries(@RequestParam(value = "name") String name) {
        seriesRepository.deleteAll();
        seriesRepository.save(new SeriesDO("Test1"));
        return seriesRepository.findByName(name);
    }
}
