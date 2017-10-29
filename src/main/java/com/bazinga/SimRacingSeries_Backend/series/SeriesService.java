package com.bazinga.SimRacingSeries_Backend.series;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import com.bazinga.SimRacingSeries_Backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    SeriesDO putSeries(@RequestBody SeriesDO series) throws IllegalAccessException {
        if(seriesRepository.findBySlugName(series.getSlugName()) != null) {
            throw new IllegalAccessException("Series already exists");
        }
        return seriesRepository.insert(series);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    SeriesDO getSeries(@RequestParam(value = "slugName") String slugName) {
        //seriesRepository.deleteAll();
        //seriesRepository.save(new SeriesDO("Test1"));
        return seriesRepository.findBySlugName(slugName);
    }
}
