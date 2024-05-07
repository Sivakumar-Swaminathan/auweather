package org.wealther.demo.utility;

import com.fasterxml.jackson.databind.JsonNode;
import org.wealther.demo.model.WeatherBase;
import org.wealther.demo.model.WeatherMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeatherMapHelper {

    public List<WeatherMap> buildWeatherData(JsonNode temperatureNode) {
        List<WeatherMap> weatherMaps = null;
        WeatherMap weatherMap = null;
        if (temperatureNode.at("/list").isArray()) {
            weatherMaps = new ArrayList<>();
            for (JsonNode listNode : temperatureNode.at("/list")) {
                weatherMap = WeatherMap.builder()
                        .tempDateTime(listNode.at("/dt_txt").asText())
                        .weatherBase(buildIterateWeatherElement(listNode))
                        .build();
                weatherMaps.add(weatherMap);
            }
        }
        return weatherMaps;
    }

    public WeatherBase buildIterateWeatherElement(JsonNode listNode) {
        WeatherBase weatherBase = WeatherBase.builder()
                .temp(handleDouble2String(listNode.at("/main/temp").asDouble()))
                .feelsLike(handleDouble2String(listNode.at("/main/feels_like").asDouble()))
                .tempMin(handleDouble2String(listNode.at("/main/temp_min").asDouble()))
                .tempMax(handleDouble2String(listNode.at("/main/temp_max").asDouble()))
                .pressure(handleDouble2String(listNode.at("/main/pressure").asDouble()))
                .seaLevel(handleDouble2String(listNode.at("/main/sea_level").asDouble()))
                .grndLevel(handleDouble2String(listNode.at("/main/grnd_level").asDouble()))
                .humidity(handleDouble2String(listNode.at("/main/humidity").asDouble()))
                .tempKf7(handleDouble2String(listNode.at("/main/temp_kf").asDouble()))
                .weather(handleNullString(listNode.at("/weather/0/main").asText()))
                .weatherDesc(handleNullString(listNode.at("/weather/0/description").asText()))
                .windSpeed(handleDouble2String(listNode.at("/wind/speed").asDouble()))
                .windDeg(handleDouble2String(listNode.at("/wind/deg").asDouble()))
                .windGust(handleDouble2String(listNode.at("/wind/gust").asDouble()))
                .visibility(handleDouble2String(listNode.at("/visibility").asDouble()))
                .build();
        return weatherBase;
    }
    public static final String handleDouble2String(Double doubleValue) {
        return Optional.ofNullable(doubleValue)
                .map(d -> doubleValue.toString())
                .orElse("");
    }

    public static final String handleNullString(String strValue) {
        return Optional.ofNullable(strValue)
                .map(d -> strValue.toString())
                .orElse("");
    }
}
