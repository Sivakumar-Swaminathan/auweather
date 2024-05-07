package org.wealther.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wealther.demo.model.WeatherMap;
import org.wealther.demo.service.WeatherMapService;

import java.util.List;

@Slf4j

@RestController
@RequestMapping(path="/weathermap/v1")
public class WeatherMapController {

    @Autowired
    private WeatherMapService weatherMapService;

    @RequestMapping(path="/dailytemp", method = RequestMethod.GET)
    ResponseEntity<Object> dailyWeather(@RequestParam String city) {
        log.info("::" + this.getClass().getSimpleName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ... loading");
        return weatherMapService.dailyWeather(city);
    }

}
