package org.wealther.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherBase {
    String temp;
    String feelsLike;
    String tempMin;
    String tempMax;
    String pressure;
    String seaLevel;
    String grndLevel;
    String humidity;
    String tempKf7;
    String weather;
    String weatherDesc;
    String windSpeed;
    String windDeg;
    String windGust;
    String visibility;
}
