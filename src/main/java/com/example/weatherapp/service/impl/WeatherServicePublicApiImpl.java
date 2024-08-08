package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.WeatherMapper;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.util.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServicePublicApiImpl implements WeatherService {
    private final HttpClient client;
    @Value("${api.weather.url}")
    private String weatherUrl;
    @Value("${api.default.city}")
    private String defaultCity;

    public Weather getWeather(String param) {
        return WeatherMapper.INSTANCE.toWeather(client.get(formatUrl(weatherUrl, param), WeatherDto.class));
    }

    private String formatUrl(String weatherUrl, String cityName) {
        return weatherUrl.replaceFirst(defaultCity, cityName);
    }
}
