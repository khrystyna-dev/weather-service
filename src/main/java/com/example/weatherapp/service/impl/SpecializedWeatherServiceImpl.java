package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.SpecializedWeatherDto;
import com.example.weatherapp.service.SpecializedWeatherService;
import com.example.weatherapp.util.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecializedWeatherServiceImpl implements SpecializedWeatherService {
    @Value("${api.default.lat}")
    private String defaultLat;
    @Value("${api.default.lon}")
    private String defaultLon;
    @Value("${api.specialized-weather.url}")
    private String specializedWeatherUrl;
    private final HttpClient client;

    public SpecializedWeatherDto getSpecializedWeather(String latitude, String longitude) {
        return client.get(formatUrl(specializedWeatherUrl, latitude, longitude), SpecializedWeatherDto.class);
    }

    private String formatUrl(String specializedWeatherUrl, String latitude, String longitude) {
        return specializedWeatherUrl
                .replaceFirst(defaultLat, latitude)
                .replaceFirst(defaultLon, longitude);
    }
}
