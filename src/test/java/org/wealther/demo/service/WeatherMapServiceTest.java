package org.wealther.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wealther.demo.model.WeatherMap;
import org.wealther.demo.utility.WeatherMapBaseError;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherMapServiceTest {

    @Autowired
    WeatherMapService weatherMapService;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_WeatherMapService_ok() throws JsonProcessingException {
        ResponseEntity<Object> responseEntity =  weatherMapService.dailyWeather("Melbourne");
        assertNotNull(responseEntity);
        System.out.println("responseEntity.getStatusCode() = " + responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_WeatherMapService_Ok_Adelaide() throws JsonProcessingException {
        ResponseEntity<Object> responseEntity =  weatherMapService.dailyWeather("Adelaide");
        assertNotNull(responseEntity);
        System.out.println("responseEntity.getStatusCode() = " + responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /***
     * Note that this is live data test
     * In some time, it may be unsuccessful due to data conditions
     *
     */
    @Test
    void test_WeatherMapService_Ok_BrowseData() throws JsonProcessingException {
        ResponseEntity<Object> responseEntity =  weatherMapService.dailyWeather("Adelaide");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<WeatherMap> weatherMaps = (List) responseEntity.getBody();
        WeatherMap firstWeatherMap = weatherMaps.get(0);
        WeatherMap lastWeatherMap = weatherMaps.get(weatherMaps.size()-1);
        assertTrue(firstWeatherMap.getTempDateTime().contains("2024-05"));      //year-on
        assertTrue(lastWeatherMap.getWeatherBase().getTemp().startsWith("29"));  // current temperature
    }

    @Test
    void test_WeatherMapService_Invalid_City() throws JsonProcessingException {

        WeatherMapBaseError thrownMap = Assertions.assertThrows(WeatherMapBaseError.class, () -> {
            weatherMapService.dailyWeather("chennai");
        }, "not found error was expected");

        Assertions.assertEquals("Record not found (check the city)", thrownMap.getMessage());

    }
}
