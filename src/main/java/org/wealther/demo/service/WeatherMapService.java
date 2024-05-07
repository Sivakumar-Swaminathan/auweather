package org.wealther.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.wealther.demo.utility.WeatherMapBaseError;
import org.wealther.demo.utility.WeatherMapHelper;
import org.wealther.demo.model.WeatherMap;
import java.util.List;

@Slf4j
@Service
public class WeatherMapService {

    private static final String AUS_CTRY_CODE_SUFFIX = ",AU";
    private static final String SERVICE_NAME = "weather-map";
    private static final String FALLBACK_METHOD = "fallback";
    @Value("${open.weather.apikey}")
    private String openWeatherApikey;
    @Autowired
    public WebClient webClient;

    private WeatherMapHelper weatherMapHelper = new WeatherMapHelper();
    private final static ObjectMapper mapper = new ObjectMapper();
    public ResponseEntity<Object>  dailyWeather(String city) {
        log.info("::" + this.getClass().getSimpleName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        JsonNode temperatureNode = null;
        List<WeatherMap> weatherMapList = null;
        try {
            String responseWeather = weatherAPIWebClient(city);
            if(responseWeather == null) {
                WeatherMapBaseError weatherMapBaseError = new WeatherMapBaseError(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(),"Unknown Error (check the request & congfig)");
                throw weatherMapBaseError;
            }
            temperatureNode = mapper.readTree(responseWeather);
            weatherMapList = weatherMapHelper.buildWeatherData(temperatureNode);
            ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(weatherMapList);
            return responseEntity;
        } catch (JsonProcessingException e) {
            WeatherMapBaseError weatherMapBaseError = new WeatherMapBaseError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Unknown Error (check the request & congfig)");
            throw weatherMapBaseError;
        } catch (WebClientResponseException exp) {
            //401 UNAUTHORIZED or Not Found
            WeatherMapBaseError weatherMapBaseError = new WeatherMapBaseError(HttpStatus.BAD_REQUEST, exp.getStatusCode().toString(), "Unknown Error (check the request & congfig)");
            if(exp != null && exp.getStatusText().contains("Unauthorized")) {
                weatherMapBaseError = new WeatherMapBaseError(HttpStatus.UNAUTHORIZED,exp.getStatusCode().toString(), "Unauthorized access (check the apikey + provider tenancy)");
            } else if (exp != null && exp.getStatusText().contains("Not Found")) {
                weatherMapBaseError = new WeatherMapBaseError(HttpStatus.NOT_FOUND,exp.getStatusCode().toString(), "Record not found (check the city)");
            }
            // WeatherMapBaseError weatherMapBaseError = new WeatherMapBaseError(HttpStatus.BAD_REQUEST, "Invalid request (check the supplied city)");
            throw weatherMapBaseError;
        } catch (Exception exp) {
            WeatherMapBaseError weatherMapBaseError = new WeatherMapBaseError(HttpStatus.BAD_REQUEST, exp.getMessage(), "Unknown Error (check the request & congfig)");
            throw weatherMapBaseError;
        }
    }

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = FALLBACK_METHOD)
    private String weatherAPIWebClient(String city) {
            String weatherApiResponse = webClient.get()
                    .uri(queryBuilder -> queryBuilder
                            .queryParam("q", city + AUS_CTRY_CODE_SUFFIX)
                            .queryParam("appid", openWeatherApikey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return weatherApiResponse;
    }
    public String fallback(Exception ex) {
        log.error(":: fallback triggered : Weather API not functioning ....");
        return null;
    }
}
