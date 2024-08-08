package com.example.weatherapp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WeatherStrategy {
    private final WeatherService openWeatherApi;
    private final WeatherService weatherApi;

    public WeatherStrategy(@Qualifier(value = "weatherServiceOpenImpl") WeatherService openWeatherApi,
                           @Qualifier(value = "weatherServicePublicApiImpl") WeatherService weatherApi) {
        this.openWeatherApi = openWeatherApi;
        this.weatherApi = weatherApi;
    }

    public WeatherService getService(String city) {
        switch (city) {
            case "Lviv", "Odesa": return openWeatherApi;
            default: return weatherApi;
        }
    }
}
