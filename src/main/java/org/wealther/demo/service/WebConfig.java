package org.wealther.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class WebConfig {

    @Value("${open.weather.url}")
    private String openWeatherUrl;
    @Bean
    public WebClient webClient() {
        log.info("::" + this.getClass().getSimpleName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        WebClient webClient = WebClient.builder()
                .baseUrl(openWeatherUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return webClient;
    }
}