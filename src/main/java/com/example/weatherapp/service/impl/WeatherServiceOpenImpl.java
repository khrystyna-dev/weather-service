package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.OpenWeatherDto;
import com.example.weatherapp.mapper.WeatherMapper;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.util.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceOpenImpl implements WeatherService {
    private final HttpClient client;
    @Value("${api.open-weather.url}")
    private String url;
    @Value("${api.default.city}")
    private String defaultCity;

    @Override
    public Weather getWeather(String param) {
        return WeatherMapper.INSTANCE.toWeather(client.get(formatUrl(url, param), OpenWeatherDto.class));
    }

    private String formatUrl(String weatherUrl, String cityName) {
        return weatherUrl.replaceFirst(defaultCity, cityName);
    }
}
