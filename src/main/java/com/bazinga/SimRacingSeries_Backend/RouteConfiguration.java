package com.bazinga.SimRacingSeries_Backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class RouteConfiguration {
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
