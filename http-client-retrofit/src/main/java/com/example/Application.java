package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.model.Weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {
    private static final String CITY_ID = "400010";
    private final WeatherService weatherService;

    @Override
    public void run(String... args) throws Exception {
        Weather weather = weatherService.getWeather(CITY_ID).get();
        log.info("{}", weather);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
