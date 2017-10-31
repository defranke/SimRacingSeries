package com.bazinga.SimRacingSeries_Backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Controller
    static class Routes {

        @RequestMapping({
                "/s/**",
                "/series/**",
                "/splash"
        })
        public String index() {
            return "forward:/index.html";
        }
    }
}
