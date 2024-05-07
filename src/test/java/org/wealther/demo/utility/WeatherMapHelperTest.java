package org.wealther.demo.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wealther.demo.model.WeatherBase;
import org.wealther.demo.model.WeatherMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class WeatherMapHelperTest {

    WeatherMapHelper weatherMapHelper = new WeatherMapHelper();

    private static final String jsonPayload_1 = "        {\n" +
            "            \"dt\": 1715050800,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 288.67,\n" +
            "                \"feels_like\": 288.13,\n" +
            "                \"temp_min\": 288.67,\n" +
            "                \"temp_max\": 288.96,\n" +
            "                \"pressure\": 1032,\n" +
            "                \"sea_level\": 1032,\n" +
            "                \"grnd_level\": 1027,\n" +
            "                \"humidity\": 71,\n" +
            "                \"temp_kf\": -0.29\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 801,\n" +
            "                    \"main\": \"Clouds\",\n" +
            "                    \"description\": \"few clouds\",\n" +
            "                    \"icon\": \"02d\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"clouds\": {\n" +
            "                \"all\": 23\n" +
            "            },\n" +
            "            \"wind\": {\n" +
            "                \"speed\": 2.4,\n" +
            "                \"deg\": 230,\n" +
            "                \"gust\": 2.35\n" +
            "            },\n" +
            "            \"visibility\": 10000,\n" +
            "            \"pop\": 0,\n" +
            "            \"sys\": {\n" +
            "                \"pod\": \"d\"\n" +
            "            },\n" +
            "            \"dt_txt\": \"2024-05-07 03:00:00\"\n" +
            "        }";
    private static final String jsonPayload_2 = "{\n" +
            "    \"cod\": \"200\",\n" +
            "    \"message\": 0,\n" +
            "    \"cnt\": 40,\n" +
            "    \"list\": [\n" +
            "        {\n" +
            "            \"dt\": 1715050800,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 288.67,\n" +
            "                \"feels_like\": 288.13,\n" +
            "                \"temp_min\": 288.67,\n" +
            "                \"temp_max\": 288.96,\n" +
            "                \"pressure\": 1032,\n" +
            "                \"sea_level\": 1032,\n" +
            "                \"grnd_level\": 1027,\n" +
            "                \"humidity\": 71,\n" +
            "                \"temp_kf\": -0.29\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 801,\n" +
            "                    \"main\": \"Clouds\",\n" +
            "                    \"description\": \"few clouds\",\n" +
            "                    \"icon\": \"02d\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"clouds\": {\n" +
            "                \"all\": 23\n" +
            "            },\n" +
            "            \"wind\": {\n" +
            "                \"speed\": 2.4,\n" +
            "                \"deg\": 230,\n" +
            "                \"gust\": 2.35\n" +
            "            },\n" +
            "            \"visibility\": 10000,\n" +
            "            \"pop\": 0,\n" +
            "            \"sys\": {\n" +
            "                \"pod\": \"d\"\n" +
            "            },\n" +
            "            \"dt_txt\": \"2024-05-07 03:00:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"dt\": 1715061600,\n" +
            "            \"main\": {\n" +
            "                \"temp\": 287.99,\n" +
            "                \"feels_like\": 287.36,\n" +
            "                \"temp_min\": 287.72,\n" +
            "                \"temp_max\": 287.99,\n" +
            "                \"pressure\": 1032,\n" +
            "                \"sea_level\": 1032,\n" +
            "                \"grnd_level\": 1026,\n" +
            "                \"humidity\": 70,\n" +
            "                \"temp_kf\": 0.27\n" +
            "            },\n" +
            "            \"weather\": [\n" +
            "                {\n" +
            "                    \"id\": 801,\n" +
            "                    \"main\": \"Clouds\",\n" +
            "                    \"description\": \"few clouds\",\n" +
            "                    \"icon\": \"02d\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"clouds\": {\n" +
            "                \"all\": 21\n" +
            "            },\n" +
            "            \"wind\": {\n" +
            "                \"speed\": 2.98,\n" +
            "                \"deg\": 203,\n" +
            "                \"gust\": 3.14\n" +
            "            },\n" +
            "            \"visibility\": 10000,\n" +
            "            \"pop\": 0,\n" +
            "            \"sys\": {\n" +
            "                \"pod\": \"d\"\n" +
            "            },\n" +
            "            \"dt_txt\": \"2024-05-07 06:00:00\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"city\": {\n" +
            "        \"id\": 2158177,\n" +
            "        \"name\": \"Melbourne\",\n" +
            "        \"coord\": {\n" +
            "            \"lat\": -37.8142,\n" +
            "            \"lon\": 144.9632\n" +
            "        },\n" +
            "        \"country\": \"AU\",\n" +
            "        \"population\": 15000,\n" +
            "        \"timezone\": 36000,\n" +
            "        \"sunrise\": 1715029588,\n" +
            "        \"sunset\": 1715066814\n" +
            "    }\n" +
            "}";
    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_handleDouble2String() {
        assertEquals("12.45", weatherMapHelper.handleDouble2String(12.45));
    }
    @Test
    void test_handleNullString() {
        String elementA = new String("cloud");
        String elementB = null;
        assertEquals("cloud", weatherMapHelper.handleNullString(elementA));
        assertEquals("", weatherMapHelper.handleNullString(elementB));
    }
    @Test
    void test_buildIterateWeatherElement() throws JsonProcessingException {
        JsonNode temperatureNode = mapper.readTree(jsonPayload_1);
        assertNotNull(temperatureNode);
        WeatherBase weatherBase = weatherMapHelper.buildIterateWeatherElement(temperatureNode);
        assertNotNull(weatherBase);
        assertEquals("288.67", weatherBase.getTemp());
        assertEquals("288.13", weatherBase.getFeelsLike());
        assertEquals("288.67", weatherBase.getTempMin());
        assertEquals("288.96", weatherBase.getTempMax());
        assertEquals("1032.0", weatherBase.getPressure());
    }
    @Test
    void test_buildWeatherData() throws JsonProcessingException {
        JsonNode temperatureNode = mapper.readTree(jsonPayload_2);
        assertNotNull(temperatureNode);
        List<WeatherMap> weatherMaps = weatherMapHelper.buildWeatherData(temperatureNode);
        assertNotNull(weatherMaps);
        assertEquals(2, weatherMaps.size());
    }
}
